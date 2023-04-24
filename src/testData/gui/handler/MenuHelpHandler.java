package testData.gui.handler;

import testData.gui.Dialog.AboutDialog;
import testData.gui.MainForm;
import testData.settings.HandlerCode;

import java.awt.event.ActionEvent;

public class MenuHelpHandler extends Handler {
    private final AboutDialog aboutDialog;

    public MenuHelpHandler(MainForm frame) {
        super(frame);
        aboutDialog = new AboutDialog(frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case HandlerCode.MENU_HELP_ABOUT -> {
                aboutDialog.setVisible(true);
                aboutDialog.setAlwaysOnTop(true);
                break;
            }
        }

    }
}
