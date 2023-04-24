package testData.generator;

import testData.exceptions.InitException;
import testData.generator.dataConverter.DataConverter;
import testData.settings.Settings;

import java.io.File;


public class Test {
    public static void main(String[] args) throws InitException {
        Settings.setFileSave(new File(System.getProperty("user.dir") + "/test.bin"));

        BaseGenerator generator = new SquareGen(-100, 100, 50, 2, 90, 0);
        testing(generator);
//31_536_000
    }

    public static void testing(BaseGenerator generator) {
        generator.generateData();

        Settings.setFileOpen(new File(System.getProperty("user.dir") + "/test.bin"));
        Settings.setFileSave(new File(System.getProperty("user.dir") + "/test.xls"));
    }
}
