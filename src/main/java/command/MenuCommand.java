
package command;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.ParserMenu;

public class MenuCommand extends BotCommand { 
    
    private ParserMenu p; 
    
    public MenuCommand(ParserMenu p) {
        super("menu", "Invia il menù giornaliero della mensa");
        this.p = p;
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage()
                        .setChatId(chat.getId().toString())
                        .setText(p.getMenu());
        
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            Logger.getLogger(MenuCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
