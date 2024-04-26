package Backend;

import Backend.Exception.DirectoryNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    /**
     * Generates a UUID.
     *
     * @return The generated {@link UUID}.
     */
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static Matcher getMatcher(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher;
    }

    public static String appendToPath(String path, String name) {
        return path + File.separator + name;
    }

    public static void handleDatabaseFileStructureCorruption(DirectoryNotFoundException e) {
        System.err.println("Database file structure is corrupted");
        System.err.println(e);
        e.printStackTrace();
        System.exit(0);
    }

    public static void handleDatabaseFileStructureCorruption(FileNotFoundException e) {
        System.err.println("Database file structure is corrupted");
        System.err.println(e);
        e.printStackTrace();
        System.exit(0);
    }
}