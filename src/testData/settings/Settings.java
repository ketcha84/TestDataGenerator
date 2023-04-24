package testData.settings;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashSet;

/**
 * @author Kachurin Nikita
 */

/**
 * todo Refactoring Settings & Text & Exceptions
 */
final public class Settings {

    final public static String MENU_MAIN_CONVERT_TITLE = "Convert";
    final public static String MENU_MAIN_EXIT_TITLE = "Exit";
    final public static String MENU_MAIN_ABOUT_TITLE = "About";

    final public static String DIALOG_PROGRESS_CONVERT_TITLE = "DIALOG_PROGRESS_CONVERT_TITLE";
    final public static String DIALOG_PROGRESS_GENERATE_TITLE = "DIALOG_PROGRESS_GENERATE_TITLE";
    final public static String DIALOG_PROGRESS_CONVERT = "CONVERT";
    final public static String DIALOG_PROGRESS_GENERATE = "GENERATE";


    final public static String SAVE_OPEN_FILE_EXT = "bin";
    final public static String EXCEL_FORMAT_EXT = "xls";
    final public static FileNameExtensionFilter filterBin =  new FileNameExtensionFilter("*.bin", SAVE_OPEN_FILE_EXT);
    final public static FileNameExtensionFilter filterXLS = new FileNameExtensionFilter("*.xls",EXCEL_FORMAT_EXT);

    final public static String OPEN_DIR = System.getProperty("user.dir");

    private static File FILE_OPEN;
    private static File FILE_SAVE;
    final public static File FILE_TEMP = new File(OPEN_DIR + "/temp.tmp");

    final public static int BUFFER_SIZE_4K = 4_096;
    final public static int BUFFER_SIZE_32K = 32_768;
    final public static int BUFFER_SIZE_262K = 262_144;
    final public static int BUFFER_SIZE_2M = 2_097_152;
//    final public static int BUFFER_SIZE_16M = 16_777_216;

    final public static HashSet<Integer> buffs_set = new HashSet<>();

    final public static String[] GENERATORS_NAMES = {"--Select--", "Sine Generator", "Square Generator", "Triangle Generator", "SawTooth Generator"};

    static {
        buffs_set.add(BUFFER_SIZE_4K);
        buffs_set.add(BUFFER_SIZE_32K);
        buffs_set.add(BUFFER_SIZE_262K);
        buffs_set.add(BUFFER_SIZE_2M);
//        buffs_set.add(BUFFER_SIZE_16M);
    }

    public static void setFileOpen(File file) {
        if (file != null) {
            FILE_OPEN = file;
        } else {
        }
    }

    public static void setFileSave(File file) {
        if (file != null) {
            FILE_SAVE = file;
        } else {
        }
    }

    public static File getFileOpen() {
        return FILE_OPEN;
    }

    public static File getFileSave() {
        return FILE_SAVE;
    }
}
