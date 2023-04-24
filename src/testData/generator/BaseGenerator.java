package testData.generator;

import testData.exceptions.InitException;
import testData.generator.times.TimeCounter;
import testData.settings.Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseGenerator extends Thread implements CancelingInterface, Percentable {
    double min;
    double max;

    double coefficient;
    double bias;
    double heightFunction;

    long period;
    long periods;
    int noiseRate;
    int pickingRate;
    /**
     * Percents--------------------------------------------------->
     */
    private volatile int percents;
    private long count;
    /**
     * Percents-------------------------------------------/>
     */

    /**
     * CancelingInterface --------------------------------------------------->
     */
    private volatile boolean cancel;
    /**
     * ------------------------------------------------------/>
     */

    /**
     * @param min         Minimal Value of Data
     * @param max         Maximal Value of Data
     * @param period      Amount of Data in one period
     * @param periods     Amount of Periods
     * @param noiseRate   Noise Rate
     * @param pickingRate Pick limiter
     * @throws InitException can throw InitException
     */

    public BaseGenerator(double min, double max, long period, long periods, int noiseRate, int pickingRate) throws InitException {
        if (max <= min) {
            throw new InitException(InitException.WRONG_RANGE);
        }
        this.max = max;
        this.min = min;
        if (period <= 0) {
            throw new InitException(InitException.WRONG_PERIOD);
        }
        this.period = period;
        this.periods = periods;
        this.noiseRate = noiseRate;
        this.pickingRate = pickingRate;

        this.bias = 0 + this.min;
        this.coefficient = (this.max + Math.abs(this.bias)) / (this.period - 1);
        heightFunction = max - min;
        this.cancel = false;
    }

    /**
     * @param step Step in period
     * @return double current value of function (Triangle, Sine, Sawtooth, Square)
     */
    abstract public double funcX(long step);


    final public void generateData() {

        long fullLength = (this.period * this.periods + this.period) * 8;

        double value;
        int buffLength = getBufferSize();
        int realSize = 0;
        ByteBuffer buffer = ByteBuffer.allocate(buffLength);

        try (FileOutputStream fos = new FileOutputStream(Settings.FILE_TEMP)) {
            Settings.FILE_TEMP.createNewFile();

            //main cycle:
            for (long i = 0; i < period; i++) {
                if (cancel) {
                    fos.close();
                    Settings.FILE_TEMP.delete();
                    return;
                }
                value = funcX(i);

//<------------ Percents ----------------------------->
                count += 8;
                this.percents = (int) (this.count * 100 / fullLength);
//------------ Percents -----------------------------/>

                if (realSize < buffLength) {
                    buffer.putDouble(value);

                } else {
                    fos.write(buffer.array(), 0, realSize);
                    realSize = 0;
                    buffer.rewind();
                }
                realSize += 8;
            }
            fos.write(buffer.array(), 0, realSize);
            //end main cycle------------------------->


        } catch (IOException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
            return;
        }
        saveData();
        Settings.FILE_TEMP.delete();
    }

    /**
     * @return int Optimal Buffer Size.
     * It calculated by division full length to Buffer size
     * and choose the best buffer.
     */
    private int getBufferSize() {
        long fullLen = period * periods * 8;
        if (fullLen < Settings.BUFFER_SIZE_4K) {
            return (int) period * 8;
        }

        Map<Long, Integer> buffs = new HashMap<>();
        Set<Integer> relative = Settings.buffs_set.stream().map(x -> buffs.put(fullLen / x, x)).collect(Collectors.toSet());
        relative = null;

        long key = buffs.keySet().stream().filter(x -> x > 0).min(Long::compare).get();

        return buffs.get(key);
    }

    private void saveData() {
        int buffLength = getBufferSize();
        long fullLength = (this.period * this.periods + this.period) * 8;

        try (FileOutputStream fos = new FileOutputStream(Settings.getFileSave())) {

            for (long i = 0; i < periods; i++) {

                try (FileInputStream fis = new FileInputStream(Settings.FILE_TEMP)) {
                    byte[] buff = new byte[buffLength];
                    while (fis.available() > 0) {
                        if (cancel) {
                            fis.close();
                            fos.close();
                            Settings.FILE_TEMP.delete();
                            Settings.getFileSave().delete();
                            return;
                        }
                        int real = fis.read(buff);

//<------------ Percents ----------------------------->
                        count += real;
                        this.percents = (int) (this.count * 100 / fullLength);
//------------ Percents -----------------------------/>
                        fos.write(buff, 0, real);
                    }

                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println(fileNotFoundException.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getNoise() {
        return (Math.random() * heightFunction + bias) * noiseRate / 100;
    }

    @Override
    public int getPercents() {
        return this.percents;
    }

    @Override
    public void run() {
        generateData();
    }

    @Override
    public synchronized void cancel() {
        this.cancel = true;
    }
}
