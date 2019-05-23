package bot;

import java.io.File;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import parser.ParserMenu;

public class Bot extends TelegramLongPollingBot{
	private final String PATH = "data/menu.pdf";
	ParserMenu p = new ParserMenu(new File(PATH));
	
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        if(update.getMessage().getText().contains("/menu")){
	        	SendMessage message = new SendMessage()
	        			.setChatId(update.getMessage().getChatId())
	        			.setText(p.getMenu());
	        	try {
					execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}	
	        }
	    }
	}
	
	public String getChannelUsername() {
		return YmlResolver.getInstance().getChannelUsername();
	}
	
	
	public String getBotUsername() {
		return YmlResolver.getInstance().getBotUsername();
	}

	@Override
	public String getBotToken() {
		return YmlResolver.getInstance().getToken();
	}
	
	private void sendMessageToChannel(String message) throws TelegramApiException {
		SendMessage sd = new SendMessage();
		sd.enableMarkdown(true);
		sd.setChatId(YmlResolver.getInstance().getChannelUsername());
		
		sd.setText(message);
		sd.enableMarkdown(true);

        execute(sd);
	}
	
	public void sendMenuToChannel() throws TelegramApiException {
		sendMessageToChannel(p.getMenu());
	}

}
