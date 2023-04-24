package testData.gui;

import testData.exceptions.InitException;
import testData.generator.BaseGenerator;
import testData.generator.SineGenerator;
import testData.generator.SquareGen;
import testData.generator.TriangleGen;
import testData.gui.menu.MainMenu;
import testData.settings.Settings;
import testData.settings.Text;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public class MainForm extends JFrame {
    private JTextField minTxt;
    private JTextField maxTxt;
    private JTextField periodTxt;
    private JTextField periodsTxt;
    private JComboBox generatorsCombo;
    private JSlider noiseSlider;
    private JSlider pickSlider;
    private JSlider dutyslider;
    private JPanel rootPanel;
    private JButton generateBtn;

    private final MainMenu mainMenu;

    public MainForm() {
        add(rootPanel);

        mainMenu= new MainMenu(this);
        setJMenuBar(mainMenu);

        setResizable(false);
        setTitle("Test data generator");
        setVisible(true);
        initCombo();
        initTxt();

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClick();
            }
        });


        minTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });
    }

    private void initTxt() {
        PlainDocument docMin = (PlainDocument) minTxt.getDocument();
        docMin.setDocumentFilter(new DigitFilter());

        PlainDocument docMax = (PlainDocument) maxTxt.getDocument();
        docMax.setDocumentFilter(new DigitFilter());

        PlainDocument docPeriod = (PlainDocument) periodTxt.getDocument();
        docPeriod.setDocumentFilter(new DigitFilter(DigitFilter.DECIMAL_DIGITS));
        PlainDocument docPeriods = (PlainDocument) periodsTxt.getDocument();

        docPeriods.setDocumentFilter(new DigitFilter(DigitFilter.DECIMAL_DIGITS));


    }

    private void initCombo() {
        Stream<String> str = Arrays.stream(Settings.GENERATORS_NAMES);
        str.forEach(s -> generatorsCombo.addItem(s));
        generateBtn.setEnabled(false);
        dutyslider.setEnabled(false);

        generatorsCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (generatorsCombo.getSelectedIndex() == 0) {
                    generateBtn.setEnabled(false);
                } else {
                    generateBtn.setEnabled(true);
                }

                if (generatorsCombo.getSelectedItem().toString().equals("Square Generator")) {
                    dutyslider.setEnabled(true);
                } else {
                    dutyslider.setEnabled(false);
                }
            }
        });
    }

    private void btnClick() {
        double min = 0, max = 0;
        long period = 0, periods = 0;
        BaseGenerator generator;

        try {
            min = Double.parseDouble(minTxt.getText());
            max = Double.parseDouble(maxTxt.getText());
            period = Long.parseLong(periodTxt.getText());
            periods = Long.parseLong(periodsTxt.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, Text.get("NUMBER_FORMAT_EXCEPTION"), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String gen = generatorsCombo.getSelectedItem().toString();
        switch (gen) {
            case "Sine Generator" -> {
                try {
                    generator = new SineGenerator(min, max, period, periods, noiseSlider.getValue(), pickSlider.getValue());
                    executeGenerator(generator);
                } catch (InitException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }
            case "Triangle Generator" -> {
                try {
                    generator = new TriangleGen(min, max, period, periods, noiseSlider.getValue(), pickSlider.getValue());
                    executeGenerator(generator);

                } catch (InitException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }
            case "Square Generator" -> {
                try {
                    generator = new SquareGen(min, max, period, periods, dutyslider.getValue(), noiseSlider.getValue());
                    executeGenerator(generator);

                } catch (InitException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }
            case "SawTooth Generator" -> {
                try {
                    generator = new TriangleGen(min, max, period, periods, noiseSlider.getValue(), pickSlider.getValue());
                    executeGenerator(generator);

                } catch (InitException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
            default -> {
                JOptionPane.showMessageDialog(this, "Select Generator", "Error!", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }

    private boolean setSaveFile() {
        MainFileChooser fc = new MainFileChooser(this);
        fc.setFileFilter(Settings.filterBin);
        int val = fc.save();
        String filename = "";
        if (val == JFileChooser.APPROVE_OPTION) {
            filename = fc.getSelectedFile().toString();
        } else {
            JOptionPane.showMessageDialog(this, Text.get("FILE_NAME"), "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!filename.substring(filename.length() - 4).equals(".bin")) {
            filename += ".bin";
        }
        Settings.setFileSave(new File(filename));
        return true;
    }

    private void executeGenerator(BaseGenerator generator) {
        if (!setSaveFile()) {
            return;
        }
        generator.start();
        ProgressDialog dialogForm = new ProgressDialog(this, Settings.DIALOG_PROGRESS_GENERATE, generator);
    }


    class DigitFilter extends DocumentFilter {
        public static final String DECIMAL_DIGITS = "\\d+";
        private static final String DIGITS = "[0-9.\\-]";
        private String regEx;

        public DigitFilter() {
            this.regEx = DIGITS;
        }

        public DigitFilter(String regEx) {
            this.regEx = regEx;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

            if (string.matches(regEx)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
            if (string.matches(regEx)) {
                super.replace(fb, offset, length, string, attrs);
            }
        }
    }
}
