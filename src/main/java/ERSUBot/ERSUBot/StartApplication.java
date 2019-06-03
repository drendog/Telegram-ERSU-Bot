package ERSUBot.ERSUBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot.Bot;
import bot.YmlResolver;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
            new Timer().scheduleAtFixedRate(new JobMensa(b), JobMensa.getAM(), TimeUnit.DAYS.toMillis(1)); // Ore 11:45
            new Timer().scheduleAtFixedRate(new JobMensa(b), JobMensa.getPM(), TimeUnit.DAYS.toMillis(1)); // Ore 18:45
            
        } catch (TelegramApiException e) {
            Logger.getLogger(StartApplication.class).error(e);
        }
    }
 
}
