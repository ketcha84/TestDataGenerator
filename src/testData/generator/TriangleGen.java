package testData.generator;

import testData.exceptions.InitException;

final public class TriangleGen extends BaseGenerator {
    private double pickLevelUp, pickLevelDown;
    private long halfPeriod;

    public TriangleGen(double min, double max, long period, long periods, int noiseRate, int pickingRate) throws InitException {
        super(min, max, period, periods, noiseRate, pickingRate);
        init();
    }

    private void init() {
        bias = 0 + super.min;
        super.coefficient = (super.max + Math.abs(super.bias)) / (period / 2);
        super.heightFunction = super.max - super.min;

        pickLevelUp = super.max * super.pickingRate * 0.01d;
        pickLevelDown = super.min * super.pickingRate * 0.01d;

        halfPeriod = period / 2;
    }

    /**
     * @param  step Step in period
     * @return double value of function Triangle in current step
     */
    @Override
    public double funcX(long step) {
        double noise = super.getNoise();
        double result;

        if (step < halfPeriod) {
            result = coefficient * step + bias + noise;
        } else {
            result = coefficient * (period - step) + bias + noise;
        }

        if (result < min || result > max) {
            result -= noise * 2;
        }

        if (result >= pickLevelUp) {
            result = pickLevelUp;
        }

        if (result <= pickLevelDown) {
            result = pickLevelDown;
        }
        return result;
    }
}
