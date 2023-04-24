package testData.generator.dataConverter;

import testData.generator.CancelingInterface;
import testData.generator.Percentable;
import testData.generator.times.TimeCounter;
import testData.settings.Settings;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataConverter extends Thread implements Percentable, CancelingInterface {

    private int percents;
    private volatile boolean cancel = false;

    private byte[] bytesBuf;
    private int buffLength;

    public DataConverter() {
        initBuffer();
    }

    private void initBuffer() {
        long filelength = Settings.getFileOpen().length();
        if (filelength < Settings.BUFFER_SIZE_4K) {
            buffLength = Settings.BUFFER_SIZE_4K;
            bytesBuf = new byte[buffLength];
            return;
        }

        Map<Long, Integer> buffs = new HashMap<>();
        Set<Integer> relative = Settings.buffs_set.stream().map(x -> buffs.put(filelength / x, x)).collect(Collectors.toSet());
        relative = null;

        long key = buffs.keySet().stream().filter(x -> x > 0).min(Long::compare).get();
        buffLength = buffs.get(key);
        bytesBuf = new byte[buffLength];
    }

    @Override
    public void run() {
        convertToExcel();
    }

    public void convertToExcel() {
        TimeCounter.setStart();
        double readData;
        long filelength = Settings.getFileOpen().length();
        long readedBytes = 0;
        StringBuilder sb = new StringBuilder();


        File saveFile = Settings.getFileSave();
        try (FileInputStream fis = new FileInputStream(Settings.getFileOpen());
//             DataInputStream dis = new DataInputStream(fis);
             FileWriter writer = new FileWriter(saveFile)) {
            while (fis.available() > 0) {
                if (cancel) {
                    fis.close();
                    writer.close();
                    saveFile.delete();
                    break;
                }


                int real = fis.read(bytesBuf);

                //----------percents--------------------------------
                readedBytes += real;
                synchronized (this) {
                    percents = (int) (readedBytes * 100 / filelength);
                }
                //-------------------------------------------------------

                //----------------------converting-------------------
                for (int i = 0; i < real; i += 8) {
                    readData = ByteBuffer.wrap(bytesBuf, i, 8).getDouble();
                    sb.append(String.format("%.2f", readData).replace('.', ',') + "\t");
                }
                writer.write(sb.toString());
                sb.delete(0, sb.length());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(TimeCounter.stopCount() + "ms.");
    }

    @Override
    public int getPercents() {
        return percents;
    }

    @Override
    public synchronized void cancel() {
        this.cancel = true;
    }
}
