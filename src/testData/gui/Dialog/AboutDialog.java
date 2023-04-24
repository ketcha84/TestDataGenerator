package testData.gui.Dialog;

import testData.gui.MainForm;
import testData.settings.Text;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class AboutDialog extends JDialog {

    public AboutDialog(MainForm frame) {
        super();
        init();
        setTitle(Text.get("DIALOG_ABOUT_TITLE"));
        setAlwaysOnTop(true);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setEnabled(true);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                frame.setEnabled(false);
            }
        });

    }

    private void init() {
        JEditorPane pane = new JEditorPane("text/html", Text.get("ABOUT"));
        pane.setEditable(false);
        pane.setOpaque(false);
        pane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        add(pane);
        pack();
        setLocationRelativeTo(null);
    }


}
