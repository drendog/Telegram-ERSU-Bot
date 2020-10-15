package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackDataHandler extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()) {
            System.out.println(update);
        }
    }

    @Override
    public String getBotUsername() {
        return YmlResolver.getInstance().getValue("BotUsername");
    }

    @Override
    public String getBotToken() {
        return YmlResolver.getInstance().getValue("token");
    }
    
}
