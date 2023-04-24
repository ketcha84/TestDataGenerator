package testData.exceptions;

import testData.settings.Text;

public class InitException extends Exception {
    public static final int WRONG_PERIOD = 0;
    public static final int WRONG_RANGE = 1;
    public static final int FILE_NAME = 2;
    public static final int DUTY_CYCLE = 3;
    public static final int DUTY_RATE = 4;
    public static final int SIN_PERIOD = 5;


    private final int code;

    public InitException(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return switch (this.code) {
            case WRONG_PERIOD -> Text.get("WRONG_PERIOD");
            case WRONG_RANGE -> Text.get("WRONG_RANGE");
            case FILE_NAME -> Text.get("FILE_NAME");
            case DUTY_CYCLE-> Text.get("DUTY_CYCLE");
            case DUTY_RATE -> Text.get("DUTY_RATE");
            case SIN_PERIOD -> Text.get("SIN_PERIOD");
            default -> "UNKNOWN ERROR";
        };
    }
}
