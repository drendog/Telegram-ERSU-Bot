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
            try {
                Files.write(Paths.get(PATH), "".getBytes(), StandardOpenOption.CREATE);
            } catch (IOException ex1) {
                org.apache.log4j.Logger.getLogger(FileManager.class).error("Errore creazione file", ex);
            }
            org.apache.log4j.Logger.getLogger(FileManager.class).error("Errore lettura file", ex);
        }
        return null;
    }
    public static void write(String str) {
        try {
            Files.write(Paths.get(PATH), "\n".concat(str).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(FileManager.class).error("Errore scrittura file", ex);
        }
    }
    
}
