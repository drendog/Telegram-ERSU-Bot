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

public class ReportCommand extends BotCommand {
    
    public ReportCommand() {
        super("report", "Segnalazioni");
    }
    
    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        String buildStr = new String(); 
        for (String str : strings) {
            buildStr += str + " ";
        }
        String usernameSender = chat.getUserName();

        StringBuilder msgBuilder = new StringBuilder("Segnalazione da @").append(usernameSender);
        msgBuilder.append("\n").append(buildStr);
        
        SendMessage adminChat = new SendMessage()
                .setChatId(YmlResolver.getInstance().getValue("admin_chat"))
                .setText(msgBuilder.toString());
        try {
            as.execute(adminChat);
        } catch (TelegramApiException ex) {
            Logger.getLogger(ReportCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        SendMessage userChat = 
                new SendMessage()
                .setChatId(chat.getId().toString())
                .setText("Segnalazione inviata");
        try {
            as.execute(userChat);
        } catch (TelegramApiException ex) {
            Logger.getLogger(ReportCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
