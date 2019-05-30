package command; 

import bot.YmlResolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import org.telegram.telegrambots.meta.api.objects.User;


public class FileBanner {
    
    private static final String PATH = YmlResolver.getInstance().getValue("blacklist");
    
    public static String read() {
        try {
            return new String(Files.readAllBytes(Paths.get(PATH)));
        } catch (IOException ex) {
            try {
                Files.write(Paths.get(PATH), "".getBytes(), StandardOpenOption.CREATE);
            } catch (IOException ex1) {
                org.apache.log4j.Logger.getLogger(FileBanner.class).error("Errore creazione file", ex);
            }
            org.apache.log4j.Logger.getLogger(FileBanner.class).error("Errore lettura file", ex);
        }
        return null;
    }
    public static void write(String str) {
        try {
            Files.write(Paths.get(PATH), "\n".concat(str).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(FileBanner.class).error("Errore scrittura file", ex);
        }
    }
    private static void clean(String str) {
        try {
            Files.delete(Paths.get(PATH));
            Files.write(Paths.get(PATH),str.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(FileBanner.class).error("Errore scrittura file", ex);
        }
    }
    
    public static List<String> getBanned() {
        String fileContent =  read(); 
        StringTokenizer tok =  new StringTokenizer(fileContent);
        
        return strTokenToList(tok); 
        
    }
    public static boolean isPresent(User user) {
        Optional<String> userBanned = getBanned().stream()
                .filter(x -> x.equals(user.getId().toString()))
                .findAny();
        return userBanned.isPresent();
    }
    private static List<String> strTokenToList(StringTokenizer token) {
        List<String> list = new ArrayList<String>(); 
        while (token.hasMoreElements()) 
            list.add(token.nextToken());
        
        return list; 
    }
    public static void remove(String userId) {
        String str = getBanned()
                .stream()
                .filter(x -> !x.equals(userId))
                .reduce("",(acc,x) -> x + "\n");
        clean(str);
        
    }
}
