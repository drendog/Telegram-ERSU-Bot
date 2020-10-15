package commands;

import org.telegram.telegrambots.meta.api.objects.Message;

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
}
