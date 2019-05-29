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
        if (strings[0].isEmpty()) noParam(as,chat);
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
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio segnalazione admin chat", ex);
        }
        SendMessage userChat = 
                new SendMessage()
                .setChatId(chat.getId().toString())
                .setText("Segnalazione inviata");
        try {
            as.execute(userChat);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio OK segnalazione utente", ex);
        }
    }
    
    private void noParam(AbsSender as, Chat chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Comando report errato. \n"
                        + "/report <b>testo della segnalazione</b>")
                .enableHtml(true);
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio FAILED segnalazione utente", ex);
        }
                
    }
}
