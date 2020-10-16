package commands;

import bot.YmlResolver;
import java.util.List;
import java.util.Optional;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BanCommandHandler extends CommandHandler {

    public BanCommandHandler() {
        super("ban");
    }
    
    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        User user = update.getMessage().getFrom();
        Chat chat = update.getMessage().getChat();

        if  (user.getId().toString().equals(chat.getId().toString())) {
            return; 
        }
        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) {
            return;
        }
        
        if (parameters.length == 0) {
            noParam(bot, chat);
            return;
        }
        Long id;
        try {
            id = Long.parseLong(parameters[0]);
        } catch (Exception e) {
            noParam(bot, chat);
            return;
        }
        
        String text = "Utente già bannato.";
        if (!FileBanner.isPresent(""+id)) {
            FileBanner.write(""+id);
            text = "Utente {" + id + "} bannato";
        }
        try {
            as.execute(new SendMessage()
                    .setChatId(chat.getId())
                    .setText(text));

        } catch (TelegramApiException ex) {

        }

    }

    private void noParam(AbsSender as, Chat chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Comando ban errato oppure non hai inserito un ID utente. \n"
                        + "/ban ID_UTENTE");
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommand.class).error("Errore invio FAILED segnalazione utente", ex);
        }
    }
}
