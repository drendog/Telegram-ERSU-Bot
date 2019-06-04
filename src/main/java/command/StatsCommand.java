package command;

import bot.YmlResolver;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;

public class StatsCommand extends BotCommand {

    public StatsCommand() {
        super("stats", "Comando che invia statistiche al gruppo degli amministratori.");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) {
            return;
        }
        SendMessage msg = new SendMessage()
                .setChatId(YmlResolver.getInstance().getValue("admin_chat"))
                .setText("Attualmente sono stati registrati: " + RegisterID.count());
        try {
            as.execute(msg);
        } catch (TelegramApiException ex) {
            Logger.getLogger(StatsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
