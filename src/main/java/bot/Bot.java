package bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import commands.CommandHandler;
import commands.HelpCommandHandler;

import messages.IMessageHandler;
import messages.AdminReplyMessageHandler;

public class Bot extends TelegramLongPollingBot {
    protected List<IMessageHandler> messageHandlers;
    protected List<CommandHandler> commandHandlers;

    public Bot() {
        messageHandlers = new ArrayList<>();
        commandHandlers = new ArrayList<>();

        messageHandlers.add(new AdminReplyMessageHandler());

        commandHandlers.add(new HelpCommandHandler());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();

            messageHandlers.stream()
                .filter(handler -> handler.shouldHandle(message))
                .forEach(handler -> handler.handleRequest(this, update));

            if(message.isCommand()) {
                commandHandlers.stream()
                    .filter(handler -> handler.shouldHandle(message))
                    .findFirst().get().handleRequest(this, update);
            }
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
