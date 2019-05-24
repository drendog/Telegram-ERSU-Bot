package ERSUBot.ERSUBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot.Bot;
import bot.YmlResolver;
import java.util.Timer;
import parser.Scraper;

public class StartApplication {

    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        Bot b = new Bot(YmlResolver.getInstance().getValue("BotUsername"));
        try {
            botsApi.registerBot(b);
            new Timer().scheduleAtFixedRate(new Scraper(b), 0, 1000*60);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
