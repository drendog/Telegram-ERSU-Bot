package job;

import bot.Bot;
import bot.YmlResolver;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.ParserMenu;
import parser.Scraper;

public class JobMensa extends TimerTask {
    private Bot bot;

    public JobMensa(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        String textMenu = ParserMenu.getInstance().getMenu(); 
        if (textMenu.contains("mensa non disponibile")) return; 
               
        if (!inRange()) return; 
        
        try {
            SendMessage message = new SendMessage()
                    .setChatId(YmlResolver.getInstance().getValue("mensa_channel"))
                    .setText(textMenu)
                    .enableHtml(true);
            bot.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(Scraper.class).error("Errore invio menù canale", ex);
        }
             
    }
    public static Calendar getAM() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 11);
        c.set(Calendar.MINUTE, 45);
        c.set(Calendar.SECOND, 0);
        return c;
    }
    public static Calendar getPM() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 45);
        c.set(Calendar.SECOND, 0);
        return c;
    }
    
    public static boolean inRange() {
        long now = new Date().getTime();
        Calendar fAM = getAM();
        fAM.set(Calendar.MINUTE, 44);
        Calendar bAM = getAM(); 
        bAM.set(Calendar.MINUTE, 46);
        long lfAM =  fAM.getTimeInMillis();
        long lbAM =  bAM.getTimeInMillis();
        
        Calendar fPM = getPM();
        fPM.set(Calendar.MINUTE, 44);
        Calendar bPM = getPM(); 
        bPM.set(Calendar.MINUTE, 46);
        long lfPM =  fPM.getTimeInMillis();
        long lbPM =  bPM.getTimeInMillis();
        
        return (lfAM < now && now < lbAM) || (lfPM < now && now < lbPM);
    }
    
}
