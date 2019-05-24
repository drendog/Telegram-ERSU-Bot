package parser;

import bot.YmlResolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManager {
    private static final String PATH = YmlResolver.getInstance().getValue("path_avvisi");
    
    public static String read() {
        try {
            return new String(Files.readAllBytes(Paths.get(PATH)));
        } catch (IOException ex) {
            System.err.println("Errore lettura file news");
        }
        return null;
    }
    public static void write(String str) {
        try {
            Files.write(Paths.get(PATH), "\n".concat(str).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.err.println("Errore scrittura file news");
        }
    }
    
}
