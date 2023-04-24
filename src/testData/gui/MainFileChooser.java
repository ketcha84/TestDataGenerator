package testData.gui;

import testData.settings.Settings;

import javax.swing.*;
import java.io.File;

public class MainFileChooser extends JFileChooser {
    private final MainForm frame;


    public MainFileChooser(MainForm frame) {
        this.frame = frame;
        setAcceptAllFileFilterUsed(false);
        setCurrentDirectory(new File(Settings.OPEN_DIR));
    }

    public int save() {
        return super.showSaveDialog(frame);
    }

    public int open() {
        return super.showOpenDialog(frame);
    }
}
