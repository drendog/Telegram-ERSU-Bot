package command;

import bot.YmlResolver;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CloseCommand extends BotCommand{

    public CloseCommand() {
        super("close","Comando che permette di arrestare l'esecuzione del bot");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) 
            return;
        
        System.exit(0);
    }
    
}
