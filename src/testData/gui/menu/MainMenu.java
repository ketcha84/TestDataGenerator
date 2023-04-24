package testData.gui.menu;


import testData.gui.MainForm;
import testData.gui.handler.Handler;
import testData.gui.handler.MenuHelpHandler;
import testData.gui.handler.MenuMainHandler;
import testData.settings.HandlerCode;
import testData.settings.Settings;

import javax.swing.*;

/**
 * @author Kachurin Nikita
 */

public class MainMenu extends JMenuBar{
    private JMenuItem exit;
    private JMenuItem about;
    private JMenuItem convert;

    //    private final MainFrame frame;
    private final MainForm frame;

    //    public MainMenu(JFrame frame) {
//        super();
//        this.frame = (MainFrame) frame;
//        init();
//    }
    public MainMenu(MainForm frame) {
        super();
        this.frame = (MainForm) frame;
        init();
    }

    private void init() {
        JMenu main = new JMenu("Main");
        JMenu help = new JMenu("Help");
        add(main);
        add(help);
        MenuMainHandler mainHandler = new MenuMainHandler(frame);
        MenuHelpHandler helpHandler = new MenuHelpHandler(frame);

        convert = addMenuItem(main, mainHandler, Settings.MENU_MAIN_CONVERT_TITLE, HandlerCode.MENU_MAIN_CONVERT);
        exit = addMenuItem(main, mainHandler, Settings.MENU_MAIN_EXIT_TITLE, HandlerCode.MENU_MAIN_EXIT);
        about = addMenuItem(help, helpHandler, Settings.MENU_MAIN_ABOUT_TITLE, HandlerCode.MENU_HELP_ABOUT);


    }

    private JMenuItem addMenuItem(JMenu menu, Handler listener, String title, String action) {
        JMenuItem item = new JMenuItem();
        item.setActionCommand(action);
        item.addActionListener(listener);
        item.setText(title);
        menu.add(item);
        return item;
    }
}
