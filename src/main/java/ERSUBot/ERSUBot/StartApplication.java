package ERSUBot.ERSUBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot.Bot;
import bot.YmlResolver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import parser.MenuDownloader;
import parser.Scraper;

public class StartApplication {
    public static void main(String[] args) {
        
        MenuDownloader.download(); // download menu mensa pdf 
        ApiContextInitializer.init();
        PropertyConfigurator.configure(YmlResolver.getInstance().getValue("log4j.properties"));
        
        Logger.getLogger(StartApplication.class).info("Start Application");
        
        TelegramBotsApi botsApi = new TelegramBotsApi();
        
        new Timer().scheduleAtFixedRate(MenuDownloader.getDownloader(), get11AM().getTime(), TimeUnit.DAYS.toMillis(7));
        Bot b = new Bot(YmlResolver.getInstance().getValue("BotUsername"));
        try {
            botsApi.registerBot(b);
            new Timer().scheduleAtFixedRate(new Scraper(b), 0, 1000*60);
        } catch (TelegramApiException e) {
            Logger.getLogger(StartApplication.class).error(e);
        }
        
    }
    
    public static Calendar get11AM() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 2);
        c.set(Calendar.HOUR_OF_DAY, 11);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 0);
        return c;
    }
     
}
