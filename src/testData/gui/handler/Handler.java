package testData.gui.handler;

import testData.gui.MainForm;

import java.awt.event.ActionListener;

public abstract class Handler implements ActionListener {
    protected final MainForm frame;

    public Handler(MainForm frame) {
        this.frame = frame;
    }

}
