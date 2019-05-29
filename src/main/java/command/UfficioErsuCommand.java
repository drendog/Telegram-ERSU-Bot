
package command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class UfficioErsuCommand extends BotCommand { 
    
    public UfficioErsuCommand() {
        super("ufficioersu", "Invia i dati dell'ufficio ERSU");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {

        String text = getText();
        SendMessage message = new SendMessage()
                        .setParseMode(ParseMode.HTML)
                        .setChatId(chat.getId().toString())
                        .setText(text);

        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(UfficioErsuCommand.class).error("Errore invio comando ufficioersu", ex);
        }
    }

    public String getText() {
        String text = "<b>ERSU Catania - Ufficio Tesserini</b>\n";
        text += "Sede della Cittadella (accanto l'ingresso della Casa dello Studente)\n\n";
        text += "🕑 <b>Orari</b>:\n";
        text += "Martedì-Giovedì dalle 9:00 alle 12:30\n\n";
        text += "<b>UfficioErsu vicino la mensa Oberdan</b>\n";
        text += "Lunedì-Mercoledì-Venerdì dalle 09:00 alle 12:30\n";
        text += "mercoledì 15:00 - 18:00\n";
        return text;
    }
    
}
