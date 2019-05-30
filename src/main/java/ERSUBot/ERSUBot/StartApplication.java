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
import job.JobMensa;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import parser.MenuDownloader;
import parser.Scraper;

public class StartApplication {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        PropertyConfigurator.configure(YmlResolver.getInstance().getValue("log4j.properties"));
        
        Logger.getLogger(StartApplication.class).info("Start Application");
        
        TelegramBotsApi botsApi = new TelegramBotsApi();
        
        new Timer().scheduleAtFixedRate(MenuDownloader.getDownloader(), 0, TimeUnit.HOURS.toMillis(1)); // Job Downloader Menù
        Bot b = new Bot(YmlResolver.getInstance().getValue("BotUsername"));
        try {
            botsApi.registerBot(b);
            new Timer().scheduleAtFixedRate(new Scraper(b), 0, 1000*60); // Job Scraping News
            new Timer().scheduleAtFixedRate(new JobMensa(b), getAM().getTime(), TimeUnit.DAYS.toMillis(1)); // Ore 11:45
            new Timer().scheduleAtFixedRate(new JobMensa(b), getPM().getTime(), TimeUnit.DAYS.toMillis(1)); // Ore 18:45
        } catch (TelegramApiException e) {
            Logger.getLogger(StartApplication.class).error(e);
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
    
     
}
