package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot{
	
	public void onUpdateReceived(Update update) {
		
	}

	public String getBotUsername() {
		return new YmlResolver().getBotUsername();
	}

	@Override
	public String getBotToken() {
		return new YmlResolver().getToken();
	}

}
