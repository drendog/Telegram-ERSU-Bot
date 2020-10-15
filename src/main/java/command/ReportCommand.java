package command;

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
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;

public class ReportCommand extends BotCommand {
    protected Map<Integer, Integer> reportsMap;
    
    public ReportCommand() {
        super("report", "Segnalazioni");

        reportsMap = new HashMap<>();
    }
    
    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        if (FileBanner.isPresent(user.getId().toString())) {
            youBanned(as,chat);
            return; 
        }
        if (strings.length == 0)  {
            noParam(as,chat);
            return; 
        }
        
        String buildStr = new String(); 
        for (String str : strings) {
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
                .setText(msgBuilder.toString())
                .setReplyMarkup(generateReportReplyKeyboardMarkup());
        try {
            as.execute(adminChat);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio segnalazione admin chat", ex);
        }
        SendMessage userChat = 
                new SendMessage()
                .setChatId(chat.getId().toString())
                .setText("La tua segnalazione è stata inviata, uno dei rappresentanti ti risponderà appena possibile");
        RegisterID.write(chat.getId().toString());
        if (!usernameFlag)
            userChat.setText("La tua segnalazione è stata inviata, ma non sarà possibile ricontattarti perché non hai un nickname."); 
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
                    + "Per inviare una richiesta o una segnalazione ai Rappresentanti ERSU scrivi il comando /report <inserisci qui la segnalazione>");
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio FAILED segnalazione utente", ex);
        }        
    }
    private void youBanned(AbsSender as, Chat chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Sei stato bandito per uso improprio del comando /report."); 
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio FAILED segnalazione utente", ex);
        }        
    }

    private static InlineKeyboardMarkup generateReportReplyKeyboardMarkup() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(1);

        List<InlineKeyboardButton> row = new ArrayList<>(1);
        row.add(new InlineKeyboardButton()
            .setText("Rispondi")
            .setCallbackData("report_reply")
        );

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
