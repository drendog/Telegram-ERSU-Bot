package bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import callback.CallbackHandler;
import callback.ReportReplyCallback;

public class CallbackDataHandler extends TelegramLongPollingBot {
    protected List<CallbackHandler> handlers;

    public CallbackDataHandler() {
        handlers = new ArrayList<>();

        // handlers.add(new ReportReplyCallback());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();

            handlers.stream()
                .filter(handler -> handler.shouldHandle(callbackData))
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
