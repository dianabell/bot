package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.List;

public class ZodiacBot extends TelegramLongPollingBot {

    public ZodiacBot(String botToken){
        super(botToken);
    }

    @Override
    public void onUpdateReceived (Update update){
        System.out.println(update);

        if (!update.hasMessage() && !update.hasCallbackQuery()) {
            return;
        }

        if(update.hasCallbackQuery()){
            processCallbackQuery(update.getCallbackQuery());
            return;
        }
        Message message = update.getMessage();

        if(!message.hasText()) {
            return;
        }

        String text = message.getText();

        if("/start".equals(text)) {
            // Отправка сообщения при команде /start
            SendMessage sendMessage = SendMessage.builder()
                    .text("Выберите месяц рождения: ")
                    .chatId(message.getChatId())
                    .replyMarkup(createSelectMonthKeybord())
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Обработка нажатия на Inline кнопку
    private void processCallbackQuery(CallbackQuery callbackQuery) {
        String callbackQueryData = callbackQuery.getData();
        System.out.println(callbackQueryData);
        try {
            execute(AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackQuery.getId())
                    .build());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        if (callbackQueryData == null){
            return;
        }

        if(callbackQueryData.equals("again")){
            // Если нажата кнопка "Попробовать еще"
            // Отправляем сообщение с предложением выбрать месяц
            SendMessage sendMessage = SendMessage.builder()
                    .text("Выберите месяц рождения: ")
                    .chatId(callbackQuery.getMessage().getChatId())
                    .replyMarkup(createSelectMonthKeybord())
                    .build();
            try {
                execute(sendMessage);

                // Удаляем предыдущее сообщение
                execute(DeleteMessage.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .build());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Month selectedMonth = null;

        try {
            selectedMonth = Month.valueOf(callbackQueryData);
        }catch (Exception e){

        }
        if (selectedMonth != null){
            // Если выбран месяц
            // Отправляем сообщение с предложением выбрать день
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(callbackQuery.getMessage().getChatId())
                    .text("Вы выбрали месяц: " + getMonthDisplayName(selectedMonth) + ". Выберите день в этом месяце: ")
                    .replyMarkup(createSelectDayKeybord(selectedMonth))
                    .build();

            try {
                execute(sendMessage);
            }catch (Exception e){
                throw new RuntimeException(e);
            }

            try {
                // Удаляем предыдущее сообщение
                execute(DeleteMessage.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .build());

            }catch (Exception e){
                throw new RuntimeException(e);
            }
            return;
        }
        if (callbackQueryData.matches("\\d+\\.\\d+")){
            // Если выбран день
            int day = Integer.parseInt(callbackQueryData.substring(0, callbackQueryData.indexOf('.')));
            int month = Integer.parseInt(callbackQueryData.substring(callbackQueryData.indexOf('.')+1));

            // Отправка сообщения с знаком зодиака
            SendMessage sendMessage = SendMessage.builder()
                    .text("Знак зодиака: " + ZodiacUtils.getSignName(day, month))
                    .chatId(callbackQuery.getMessage().getChatId())
                    .build();
            try {
                execute(sendMessage);
            }catch (Exception e){
                throw new RuntimeException(e);
            }

            try {
                // Удаляем предыдущее сообщение
                execute(DeleteMessage.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .build());

            }catch (Exception e){
                throw new RuntimeException(e);
            }

            try {
                // Отправка сообщения с кнопкой "Попробовать еще"
                execute(SendMessage.builder()
                        .text("Чтобы определить знак зодиака для другой даты, нажмите кнопку. ")
                        .chatId(callbackQuery.getMessage().getChatId())
                        .replyMarkup(createAgainKeyboard())
                        .build());
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public String getBotUsername(){
        return "AstrOracle_bot";
    }

    // Создание Inline клавиатуры для кнопки "Попробовать еще"
    private ReplyKeyboard createAgainKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(InlineKeyboardButton.builder()
                        .text("Попробовать еще")
                        .callbackData("again")
                        .build())))
                .build();
    }

    // Создание Inline клавиатуры для выбора месяца рождения
    private ReplyKeyboard createSelectMonthKeybord() {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>(6);

        Month[] months = Month.values();

        for (int i = 0; i < months.length / 2; i++){
            Month month1 = months[i];
            Month month2 = months[i + 6];

            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text(getMonthDisplayName(month1))
                    .callbackData(month1.name())
                    .build();
            InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                    .text(getMonthDisplayName(month2))
                    .callbackData(month2.name())
                    .build();

            List<InlineKeyboardButton> keyboardRow = Arrays.asList(button1, button2);

            keyboardRows.add(keyboardRow);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .build();

    }

    // Создание Inline клавиатуры для выбора дня рождения
    private ReplyKeyboard createSelectDayKeybord(Month month) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>(6);

        for (int i = 0; i < 7; i++){
            List<InlineKeyboardButton> keyboardRow = new ArrayList<>(5);

            for(int j = 0; j < 5; j++){
                int day = i + j * 7 + 1;

                String buttonText = day <= month.maxLength() ? String.valueOf(day) : "x";

                String  buttonData = day <= month.maxLength() ? day + "." + month.getValue() : "x";

                InlineKeyboardButton button = InlineKeyboardButton.builder()
                        .text(buttonText)
                        .callbackData(buttonData)
                        .build();

                keyboardRow.add(button);
            }
            keyboardRows.add(keyboardRow);
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .build();
    }

    // Получение отображаемого имени месяца на русском языке
    private static String getMonthDisplayName(Month month) {
        String name = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));

        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

}
