package commands;

import bot.YmlResolver;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;

public class StatsCommandHandler extends CommandHandler {
    public StatsCommandHandler() {
        super("stats");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) {
            return;
        }
        SendMessage msg = new SendMessage()
                .setChatId(YmlResolver.getInstance().getValue("admin_chat"))
                .setText("Attualmente sono stati registrati: " + RegisterID.count());
        try {
            bot.execute(msg);
        } catch (TelegramApiException ex) {
            Logger.getLogger(StatsCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
