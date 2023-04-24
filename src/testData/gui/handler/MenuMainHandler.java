package testData.gui.handler;

import testData.generator.CancelingInterface;
import testData.generator.dataConverter.DataConverter;
import testData.gui.Dialog.ErrorDialog;
import testData.gui.MainFileChooser;
import testData.gui.MainForm;
import testData.gui.ProgressDialog;
import testData.settings.HandlerCode;
import testData.settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MenuMainHandler extends Handler {
    private final MainFileChooser fc;

    public MenuMainHandler(MainForm frame) {
        super(frame);
        fc = new MainFileChooser(frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case HandlerCode.MENU_MAIN_CONVERT -> {
                fc.setFileFilter(Settings.filterBin);
                int returnVal = fc.open();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Settings.setFileOpen(fc.getSelectedFile());
                } else {
                    ErrorDialog.show(frame, "FILE_NAME");
                    break;
                }
                fc.setFileFilter(Settings.filterXLS);
                returnVal = fc.save();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = fc.getSelectedFile().toString();
                    if (!filename.substring(filename.length() - 4).equals(".xls")) {
                        filename += ".xls";
                    }
                    Settings.setFileSave(new File(filename));

                    Thread converter = new DataConverter();
                    converter.start();
                    ProgressDialog progress = new ProgressDialog(frame, Settings.DIALOG_PROGRESS_CONVERT, (CancelingInterface) converter);
                } else {
                    ErrorDialog.show(frame, "FILE_NAME");
                    break;
                }
                break;
            }
            case HandlerCode.MENU_MAIN_EXIT -> {
                System.exit(0);
                break;
            }
        }
    }
}
