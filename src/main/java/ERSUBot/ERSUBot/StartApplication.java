package ERSUBot.ERSUBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot.Bot;

public class StartApplication {

	public static void main(String[] args) {
		//YmlResolverTest t1 = new YmlResolverTest();
		//PdfExtracterTest t2 = new PdfExtracterTest();
		//t1.testToken();
		//t1.testBotUsername();
		//t2.testExtracter();
		//t2.testParser();
		
		ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
        	Bot b = new Bot();
            botsApi.registerBot(b);
            b.sendMenuToChannel();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
