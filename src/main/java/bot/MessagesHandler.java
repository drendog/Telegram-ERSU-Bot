package bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import messages.IMessageHandler;
import messages.AdminReplyMessageHandler;

public class MessagesHandler extends TelegramLongPollingBot {
    protected List<IMessageHandler> handlers;

    public MessagesHandler() {
        handlers = new ArrayList<>();

        handlers.add(new AdminReplyMessageHandler());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            handlers.stream()
                .filter(handler -> handler.shouldHandle(update.getMessage()))
                .forEach(handler -> handler.handleRequest(this, update));
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
