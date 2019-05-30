package command;

import bot.YmlResolver;
import java.util.List;
import java.util.Optional;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BanCommand extends BotCommand {

    public BanCommand() {
        super("ban", "Ban comando report");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        if  (user.getId().toString().equals(chat.getId().toString())) {
            System.out.println("Chat == User pls");
            return; 
        }
        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) {
            return;
        }
        
        if (strings.length == 0) {
            noParam(as, chat);
            return;
        }
        Integer id;
        try {
            id = Integer.parseInt(strings[0]);
        } catch (Exception e) {
            noParam(as, chat);
            return;
        }

        List<String> usersBanned = FileBanner.getBanned();

        
        String text = "Utente già bannato.";
        if (FileBanner.isPresent(user)) {
            FileBanner.write(id.toString());
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
