import java.net.URL;

public class Dir {
    private static String thisClassFile = "Dir.class";
    private static Dir myInstance = new Dir();

    public static final String cur = currentDir();

    private static String currentDir() {
        URL url = myInstance.getClass().getClassLoader().getResource(thisClassFile);

        if (url == null) {
            return "";
        }
        String full = url.getFile();
        return full.substring(0, full.length() - thisClassFile.length());
    }
}