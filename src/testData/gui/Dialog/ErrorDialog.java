package testData.gui.Dialog;

import testData.gui.MainForm;
import testData.settings.Text;

import javax.swing.*;

/**
 * @author Kachurin Nikita
 */
public class ErrorDialog {
    public static void show(MainForm frame, String text) {
        JOptionPane.showMessageDialog(frame, Text.get(text), Text.get("ERROR"), JOptionPane.ERROR_MESSAGE);

    }
}
