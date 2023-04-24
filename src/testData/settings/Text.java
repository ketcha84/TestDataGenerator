package testData.settings;

import java.util.Calendar;
import java.util.HashMap;

public class Text {
    private final static HashMap<String, String> data = new HashMap<>();

    static {
        addErrors();
        addProgramConstants();
        addAbout();

    }

    private static void addProgramConstants() {
        data.put("PROGRAM_NAME", "Test data generator.");
        data.put("SAVE_FILE_EXTENSION", "*.bin");
        data.put("ERROR", "Error!");
        data.put("VERSION", "1.0");
        data.put("DIALOG_ABOUT_TITLE", "About");
        data.put("DIALOG_PROGRESS_CONVERT_TITLE", "Converting");
        data.put("DIALOG_PROGRESS_GENERATE_TITLE", "Generate");
        data.put("BTN_CANCEL_TITLE", "Cancel");

    }

    private static void addAbout() {
        data.put("ABOUT", "<body style= 'font-size: 120%; text-align: center; width: 350px;'>" +
                "<h1>Test Data Generator</h1><p><img src='file:images/main2.png' width=\"250\" height=\"197\" /></p>" +
                "<p>This program was created by Kachurin Nikita</p><br />" +
                "Email: <a style ='font-weight: bolt;' href='mailto:kachurin.nikita84@gmail.com'>kachurin.nikita84@gmail.com</a>" +
                "<br />" +
                "Version: " + Text.get("VERSION") + "</p><br />" +
                "<p>Copyright " + Calendar.getInstance().get(Calendar.YEAR) + "</p></body>");
    }

    public static String get(String key) {
        return data.get(key);
    }


    private static void addErrors() {
        data.put("NUMBER_FORMAT_EXCEPTION", "Необходимо ввести правильные числа!");
        data.put("WRONG_PERIOD", "Неверно указан период.");
        data.put("WRONG_RANGE", "Неверно указан диапазон минимальных и максимальных значений.");
        data.put("FILE_NAME", "Неверное имя файла, либо его отсутствие");
        data.put("DUTY_CYCLE", "Скважность должна быть указана в процентах от 0 до 100");
        data.put("DUTY_RATE", "Для данного периода сигнала, слишком малая скважность сигнала");
        data.put("SIN_PERIOD", "Для синусоидального сигнала период не должен быть меньше 360");
    }
}