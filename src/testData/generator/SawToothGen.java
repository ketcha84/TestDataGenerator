package testData.generator;

import testData.exceptions.InitException;

/**
 * k - coefficient
 * b - bias
 * h - height of function
 */

final public class SawToothGen extends BaseGenerator {
    private double pickLevel;

    public SawToothGen(double min, double max, long period, long periods, int noiseRate, int pickingRate) throws InitException {
        super(min, max, period, periods, noiseRate, pickingRate);
        init();
    }

    private void init() {
        pickLevel = (heightFunction * pickingRate / 100) + bias;
    }

    @Override
    public double funcX(long step) {
        double noise = super.getNoise();
        double result = coefficient * step + bias + noise;

        if (result < min || result > max) {
            result -= noise * 2;
        }

        return Math.min(pickLevel, result);
    }
}
