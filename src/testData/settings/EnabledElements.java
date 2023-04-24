package testData.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR Kachurin Nikita
 */
public class EnabledElements {
    private static final Map<String, ArrayList<String>> enElMap = new HashMap<>();

    static {
        addsineElements();
        addSawToothGen();
        addSquareGen();
    }

    private static void addSquareGen() {
        ArrayList<String> elementsList = new ArrayList<>();
        elementsList.add("MAX_VAL");
        elementsList.add("MIN_VAL");
        elementsList.add("PERIOD");
        elementsList.add("PERIODS_AMOUNT");
        elementsList.add("NOISE_RATE");
        elementsList.add("NOISE_CHECKBOX");

        enElMap.put("SAW_TOOTH_GENERATOR", elementsList);
    }

    private static void addSawToothGen() {
        ArrayList<String> elementsList = new ArrayList<>();
        elementsList.add("MAX_VAL");
        elementsList.add("MIN_VAL");
        elementsList.add("PERIOD");
        elementsList.add("PERIODS_AMOUNT");

        elementsList.add("PICK_LEVEL_LIMITER");
        elementsList.add("NOISE_RATE");
        elementsList.add("NOISE_CHECKBOX");
        enElMap.put("SAW_TOOTH_GENERATOR", elementsList);
    }

    private static void addsineElements(){
        ArrayList<String> elementsList = new ArrayList<>();
        elementsList.add("MAX_VAL");
        elementsList.add("MIN_VAL");
        elementsList.add("PERIOD");
        elementsList.add("PERIODS_AMOUNT");
        elementsList.add("PICK_LEVEL_LIMITER");
        elementsList.add("NOISE_RATE");
        elementsList.add("NOISE_CHECKBOX");

        enElMap.put("SINE_GENERATOR",elementsList);
    }
}
