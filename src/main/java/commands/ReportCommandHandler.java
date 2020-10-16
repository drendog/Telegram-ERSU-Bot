package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.YmlResolver;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;
import service.ReportsRegistry;

public class ReportCommandHandler extends CommandHandler {
    public ReportCommandHandler() {
        super("report");
    }
    
    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        User user = update.getMessage().getFrom();
        Chat chat = update.getMessage().getChat();

        if (FileBanner.isPresent(user.getId().toString())) {
            youBanned(bot, chat);
            return; 
        }
        if (parameters.length == 0)  {
            noParam(bot, chat);
            return; 
        }
        
        String buildStr = new String(); 
        for (String str : parameters) {
            buildStr += str + " ";
        }
        boolean usernameFlag = true; // Ha il nickname
        String usernameSender = user.getUserName(); 
        if (usernameSender == null || usernameSender.isEmpty()) {
            usernameSender = user.getFirstName() + " " + user.getLastName(); 
            usernameFlag = false; // Non ha il nickname
        }
        
        else usernameSender = "@"+usernameSender; 
        StringBuilder msgBuilder = new StringBuilder("Segnalazione da ").append(usernameSender);
        msgBuilder.append("\n{id: ").append(user.getId()).append("}");
        msgBuilder.append("\n").append(buildStr);
        
        SendMessage adminChat = new SendMessage()
                .setChatId(YmlResolver.getInstance().getValue("admin_chat"))
                .setText(msgBuilder.toString());
                
        try {
            Message reportAdminMessage = bot.execute(adminChat);

            ReportsRegistry.getInstance().registerReport(reportAdminMessage.getMessageId(), chat.getId());
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommandHandler.class).error("Errore invio segnalazione admin chat", ex);
        }

        SendMessage userChat = 
                new SendMessage()
                .setChatId(chat.getId().toString())
                .setText("La tua segnalazione è stata inviata, uno dei rappresentanti ti risponderà appena possibile");
        RegisterID.write(chat.getId().toString());

        if (!usernameFlag)
            userChat.setText("La tua segnalazione è stata inviata, ma non sarà possibile ricontattarti direttamente perché non hai un nickname. Il bot provvederà ad inoltrati le risposte dei rappresentanti."); 

        try {
            bot.execute(userChat);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommandHandler.class).error("Errore invio OK segnalazione utente", ex);
        }
    }
    
    private void noParam(AbsSender as, Chat chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Comando report errato. \n"
                    + "Per inviare una richiesta o una segnalazione ai Rappresentanti ERSU scrivi il comando /report <inserisci qui la segnalazione>");
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommandHandler.class).error("Errore invio FAILED segnalazione utente", ex);
        }        
    }
    private void youBanned(AbsSender as, Chat chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Sei stato bandito per uso improprio del comando /report."); 
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommandHandler.class).error("Errore invio FAILED segnalazione utente", ex);
        }        
    }
}
