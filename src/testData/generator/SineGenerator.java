package testData.generator;

import testData.exceptions.InitException;

/**
 * f(x) = k*sin(x)+b+n
 *
 * k - coefficient
 * b- bias
 * n - noise
 */
final public class SineGenerator extends BaseGenerator {

    private double pickLevelUp, pickLevelDown;


    public SineGenerator(double min, double max, long period, long periods, int noiseRate, int pickingRate) throws InitException {
        super(min, max, period, periods, noiseRate, pickingRate);
        init();
    }

    private void init() {
        super.coefficient = (max - min) / 2;
        super.bias = 0 + (max + min) / 2;
        pickLevelUp = super.max * super.pickingRate * 0.01d;
        pickLevelDown = super.min * super.pickingRate * 0.01d;
    }

    /**
     *
     * @param step current step in period type long
     * @return double value of function sin() in current step
     */
    @Override
    public double funcX(long step) {
        double radians = Math.toRadians(360 / (period * 1d) * step);
        double noise = super.getNoise();

        double result = coefficient * Math.sin(radians) + bias;
        result += noise;

        if (result > super.max || result < super.min) {
            result -= 2 * noise;
        }
        if (result > pickLevelUp) {
            result = pickLevelUp;
        }
        if (result < pickLevelDown) {
            result = pickLevelDown;
        }
        return result;
    }
}