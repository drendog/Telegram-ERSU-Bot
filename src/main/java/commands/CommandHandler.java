package commands;

import java.util.Arrays;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import messages.IMessageHandler;

public abstract class CommandHandler implements IMessageHandler {
    protected String commandKey;

    protected CommandHandler(String commandKey) {
        this.commandKey = commandKey;
    } 

    public boolean shouldHandle(Message message) {
        String command = message.getText().substring(1);
        
        return command.equals(commandKey);
    }

    public void handleRequest(AbsSender bot, Update update) {
        String[] textFragments = update.getMessage().getText().split(" ");
        String[] parameters = Arrays.copyOfRange(textFragments, 1, textFragments.length);

        this.handleRequest(bot, update, parameters);
    }

    public abstract void handleRequest(AbsSender bot, Update update, String[] parameters);
}
