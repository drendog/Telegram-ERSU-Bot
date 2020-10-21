package commands;

import bot.YmlResolver;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CloseCommandHandler extends CommandHandler {
    public CloseCommandHandler() {
        super("close");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) 
            return;
        
        System.exit(0);
    }
    
}
