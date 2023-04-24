package testData.generator;

import testData.exceptions.InitException;

final public class SquareGen extends BaseGenerator {
   final private int dutyCycle;

    public SquareGen(double min, double max, long period, long periods, int dutyCycle, int noiseRate) throws InitException {
        super(min, max, period, periods, noiseRate, 100);
        if (dutyCycle < 0 || dutyCycle > 100) {
            throw new InitException(InitException.DUTY_CYCLE);
        }
        this.dutyCycle = dutyCycle;
        if (countHighCycle() < 1d && dutyCycle != 0) {
            throw new InitException(InitException.DUTY_RATE);
        }

    }

    /**
     * @return returns length of high level
     */
    private long countHighCycle() {
        if (this.dutyCycle == 0) {
            return 0;
        } else {
            return (long) (this.period * this.dutyCycle * 0.01d);
        }
    }

    /**
     * @param step current step in period
     * @return double value of function funcX() on current step
     */
    @Override
    public double funcX(long step) {
        long highSygnal = countHighCycle();
        double result;
        double noise = super.getNoise();

        if (step <= highSygnal) {
            result = super.max;
        } else {
            result = super.min;
        }
        result += noise;
        if (result < super.max && result > super.min) {
            return result;
        } else {
            return (result - 2 * noise);
        }
    }
}