package testData.gui;

import testData.generator.CancelingInterface;
import testData.generator.Percentable;
import testData.settings.Settings;
import testData.settings.Text;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgressDialog extends JFrame {
    private JPanel rootPanel;
    private JLabel label;
    private JProgressBar progressBar;
    private JButton cancelButton;

    private final CancelingInterface process;
    private final String action;
    private MainForm frame;

    private boolean aborted = false;

    public ProgressDialog(MainForm frame, String action, CancelingInterface process) {
        super();
        this.action = action;
        this.frame = frame;
        this.process = process;
        initBtn();
        init();
    }

    private void initBtn() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process.cancel();
            }
        });
    }

    private void init() {
        add(rootPanel);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        frame.setEnabled(false);
        addWinClose();

        switch (action) {
            case Settings.DIALOG_PROGRESS_CONVERT -> {
                setTitle(Text.get(Settings.DIALOG_PROGRESS_CONVERT_TITLE));
                label.setText(Text.get(Settings.DIALOG_PROGRESS_CONVERT_TITLE));
            }
            case Settings.DIALOG_PROGRESS_GENERATE -> {
                setTitle(Text.get(Settings.DIALOG_PROGRESS_GENERATE_TITLE));
                label.setText(Text.get(Settings.DIALOG_PROGRESS_GENERATE_TITLE));
            }
        }
        new Thread() {
            @Override
            public void run() {
                if (process instanceof Thread) {
                    while (((Thread) process).isAlive()) {
                        try {
                            setProgress(((Percentable) process).getPercents());
                            Thread.sleep(30);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    setProgress(100);
                    if (!aborted) {
                        JOptionPane.showMessageDialog(ProgressDialog.super.rootPane, "Done!");
                    }
                    dispose();
                }
            }
        }.start();
    }

    private void addWinClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                frame.setEnabled(true);
                frame.requestFocus();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (!((Thread) process).isAlive()) {
                    frame.setEnabled(true);
                    frame.requestFocus();
                } else {
                    aborted = true;
                    process.cancel();
                    JOptionPane.showMessageDialog(ProgressDialog.this, "Process Aborted", "Aborted", JOptionPane.ERROR_MESSAGE);

                    frame.setEnabled(true);
                    frame.requestFocus();
                    dispose();
                }
            }
        });
    }

    public void setProgress(int percents) {
        this.progressBar.setValue(percents);
    }
}
