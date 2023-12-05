package org.example;
import java.time.LocalDate;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
public class ZodiacUtils {

    public static void watchVideo(String videoId){
        String url = videoId;
        try{
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }
    }
    public static String getSignName(int day, int month) {
        try {
            LocalDate.parse(String.format("2024-%02d-%02d", month, day));
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect day or month. Month: " + month + ", day: " + day, e);
        }

        String signName;

        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
            signName = "Овен";
        } else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
            signName = "Телец";
        } else if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) {
            signName = "Близнецы";
        } else if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) {
            signName = "Рак";
        } else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
            signName = "Лев";
        } else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
            signName = "Дева";
        } else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
            signName = "Весы";
        } else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
            signName = "Скорпион";
        } else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
            signName = "Стрелец";
        } else if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
            signName = "Козерог";
        } else if ((month == 1 && day >= 20) || (month == 2 && day <= 20)) {
            signName = "Водолей";
        } else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
            signName = "Рыбы";
        } else {
            throw new IllegalArgumentException("Month: " + month + ", day: " + day);
        }

        // Выводим имя знака зодиака
        String message = signName;

        // Выводим описание знака зодиака
        String signDescription = getSignDescription(signName);
        message += "\n";
        message += "\nОписание знака зодиака: " + signDescription;

        String videoId = getYouTubeVideoId(signName);
        if(!videoId.isEmpty()){
            message += "\n";
            message += "\n Также можете посмотреть видео характеристику по этому знаку: " + buildYouTubeVideoLink(videoId);
        }

        return message;
    }

    private static String buildYouTubeVideoLink(String videoId) {
        return videoId;
    }


    private static String getSignDescription(String signName) {
        // Описание знака
        if (signName.equals("Овен")) {
            return "\nЗнак Овна – это самый радикальный знак огня. Люди, рожденные под знаком Овна, обладают энергичностью, инициативностью, напористостью, эмоциональностью, самостоятельностью, имеют лидерские задатки. Овны лишены страха, резки, бывают агрессивны, редко заканчивают начатые дела.\n";
        } else if (signName.equals("Телец")) {
            return "\nЗнак Тельца – этот знак характеризуется постоянством. Люди-Тельцы неспешны, работоспособны. Тельцы экономны, для них важен уют в доме. Осторожность, последовательность, упорство это качества характера Тельцов. Но они не любят перемен.\n";
        } else if (signName.equals("Близнецы")) {
            return "\nЗнак Близнецов – самый переменчивый воздушный знак. Близнецы любопытны, переменчивы, общительны, сообразительны, обладают живым умом и отличным интеллектом. Близнецы схватывают информацию на лету. Близнецы способны заниматься несколькими делами одновременно.\n";
        } else if (signName.equals("Рак")) {
            return "\nЗнак Рака – это радикальный водный знак. Характер Рака можно описать как чуткий, сентиментальный, наблюдательный, обладает глубокой интуицией. Бережно относится к вещам. Раки также высокомерны, пассивны, ленивы, дотошны до мелочей. Гибкость при любых ситуациях, но в то же время Раки имеют стержень. Эмоциональны, переменчивы в настроении. Бывают очень активны, коммуникабельны и работоспособны.\n";
        } else if (signName.equals("Лев")) {
            return "\nЗнак Льва – фиксированный знак огня. Люди, рожденные под этим знаком, любят быть приметными, играют на публику, самостоятельны, требуют поклонения своей персоне, бесстрашны, обожают славу. Они очень упрямы. Гордость – их порок. Не умеют подстраиваться под обстоятельства и людей. Не видят вокруг ничего и никого, кроме себя.\n";
        } else if (signName.equals("Дева")) {
            return "\nЗнак Девы – переменчивый знак земли. Характер Дев можно описать следующим образом – придирчивы к выбору знакомых и друзей, обожают порядок, следуют своим четким правилам. Обладают аналитическим умом. Дотошны к мелочам. Девы очень коммуникабельны, сентиментальны. Легко адаптируются к переменам в жизни, легко подстраиваются к различным обстоятельствам.\n";
        } else if (signName.equals("Весы")) {
            return "\nЗнак Весы – это воздушный знак. Люди знака Весов обладают следующими качествами – справедливость, имеют свое независимое мнение, стремятся к гармонии, талантливость, эмоциональность, в то же время очень спокойные внешне. Найдут общий язык с любым человеком. Весы нерешительны и непостоянны.\n";
        } else if (signName.equals("Скорпион")) {
            return "\nЗнак Скорпиона – радикальный водный знак. Скорпионы замкнуты, имеют внутреннее напряжение, имеют острый ум. Хорошо разбираются в людях. Закрыты для общества. Скорпионы эмоциональны, все ситуации воспринимают близко к сердцу. Не любят перемен. Самый агрессивный знак из всех. Им присуща жестокость, резкость, склочность. Любят управлять и манипулировать окружающими.\n";
        } else if (signName.equals("Стрелец")) {
            return "\nЗнак Стрельца – переменчивый знак огня. Люди-Стрельцы обладают такими качествами как жизнелюбие, с оптимизмом смотрят на жизнь, активны. Стрельцы разносторонни, увлекаются различными сферами деятельности. Стрельцы неусидчивы, обожают свободу. Стремятся все делать лучше других. Дальновидность, осторожность, ненадежность, бестактность это все про Стрельцов. Эти люди имеют талант переубеждать, хорошо «подвешен язык».\n";
        } else if (signName.equals("Козерог")) {
            return "\nЗнак Козерога – это радикальный знак земли. На людей, рожденных под знаком Козерога, можно положиться в любой ситуации. Козероги основательно подходят ко всему, за что бы ни брались. Они имеют неиссякаемый запас терпения. Вдумываются в каждую мелочь. Козероги – сами себе на уме. Легко справляются с любыми задачами, серьезный подход к делам, да и во всем. Их притягивают деньги. В то же время Козероги депрессивны, имеют страх перед проблемами. Люди-Козероги излишне упрямы, у них высокие амбиции, прагматичны. Козероги очень упертые, умеют принимать решения. Хоть они и боятся проблем, но с Козерогов проблемы «как с гуся вода».\n";
        } else if (signName.equals("Водолей")) {
            return "\nЗнак Водолея – фиксированный знак воздуха. Водолеи – хорошие изобретатели. Заводят много друзей, потому что они очень коммуникабельны. Водолеи имеют эксцентричный нрав. Люди-Водолеи самостоятельны, толерантны. В них есть такое качество как человеколюбие. Чересчур самоуверены. В голове у людей этого знака много идей, которые они могут и не осуществить.\n";
        } else if (signName.equals("Рыбы")) {
            return "\nЗнак Рыбы – переменчивый знак воды. Люди, рожденные под этим знаком, очень чутки, переменчивы, мечтательны. Ко всему этому у них очень развита интуиция. Не стремятся к переменам. Имеют богатую фантазию. Рыбы любят драматизировать. Неспособность к принятию решений.";
        }

        return "";
    }
    private static String getYouTubeVideoId(String signName) {
        // Сопоставление названий знаков с идентификаторами видео на YouTube
        String videoId = "";
        if (signName.equals("Овен")) {
            videoId = "https://youtu.be/PoGNuiURkkY?si=YyfHbUAO4jHf4N_F";
        } else if (signName.equals("Телец")) {
            videoId = "ttps://youtu.be/lE-Bn3atjDc?si=qrh1EXTNBQuEhNth";
        } else if (signName.equals("Близнецы")) {
            videoId = "https://youtu.be/u3XG6gt_ov4?si=tclRhkL8ftPyYLaz";
        } else if (signName.equals("Рак")) {
            videoId = "https://youtu.be/J6OGJYnOJxQ?si=2NmNBC3asU1fUOIX";
        } else if (signName.equals("Лев")) {
            videoId = "https://youtu.be/HsgMBIIajdY?si=ITDez8LtMPenSuz4";
        } else if (signName.equals("Дева")) {
            videoId = "https://youtu.be/5e2jPle_Edc?si=4zGvAN1Od1wyLc03";
        } else if (signName.equals("Весы")) {
            videoId = "https://youtu.be/EUeveEbEtV4?si=dBQamUyiMnDFIvZ2";
        } else if (signName.equals("Скорпион")) {
            videoId = "https://youtu.be/6xeZbJWAulw?si=iNL45nb76pVjKQ5e";
        } else if (signName.equals("Стрелец")) {
            videoId = "https://youtu.be/lxRiwmzhXe0?si=c5WB6jVpIP0dEPg_";
        } else if (signName.equals("Козерог")) {
            videoId = "https://youtu.be/wLrGiCSwyi0?si=Ch2uHGl8qZfjRCSf";
        } else if (signName.equals("Водолей")) {
            videoId = "https://youtu.be/fWjUnKrwEX0?si=VFnH9Zq4pMnm_CRL";
        } else if (signName.equals("Рыбы")) {
            videoId = "https://youtu.be/ejJOsjbRFYg?si=xkOBs9WROsoRw5O-";
        }

        // Генерация URL-адреса видео на YouTube
        if (!videoId.isEmpty()) {
            return  videoId;
        } else {
            return "No video available";
        }
    }
}
