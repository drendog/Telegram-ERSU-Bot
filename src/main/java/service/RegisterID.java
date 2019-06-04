package service;

import bot.YmlResolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class RegisterID {
    
    private static final String PATH = YmlResolver.getInstance().getValue("path_id");
    
    public static List<String> read() {
        try {
            return Files.readAllLines(Paths.get(PATH));
        } catch (IOException ex) {
            try {
                Files.write(Paths.get(PATH), "".getBytes(), StandardOpenOption.CREATE);
                return Files.readAllLines(Paths.get(PATH));
            } catch (IOException ex1) {
                org.apache.log4j.Logger.getLogger(RegisterID.class).error("Errore creazione file", ex);
            }
            org.apache.log4j.Logger.getLogger(RegisterID.class).error("Errore lettura file", ex);
        }
        return null;
    }
    
    public static void write(String id) {
        if (isRegistered(id)) return;
        try {
            Files.write(Paths.get(PATH), (id + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(RegisterID.class).error("Errore scrittura file", ex);
        }
    }
    public static boolean isRegistered(String str) {
        return read().contains(str);
    }
    public static int count() {
        return read().size(); 
    }
    
}

