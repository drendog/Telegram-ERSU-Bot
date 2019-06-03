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
        if (chat.isUserChat()) message.setReplyMarkup(bot.Bot.generateRKM());
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(UfficioErsuCommand.class).error("Errore invio comando ufficioersu", ex);
        }
    }

    public String getText() {
        String text = "<b>ERSU Catania</b>\n\n";
	    text += "📍<a href=\"https://www.google.com/maps/dir//Via+Etnea,+570,+95128+Catania+CT/\"> Via Etnea, 570</a>\n\n";
        text += "🕑 Orari di ricevimento:\n";
        text += "Lunedì 09:00 - 12:00\n";
        text += "Mercoledì 15:00 - 18:00\n";
        text += "Venerdì 09:00 - 12:00\n\n";
        text += "Tel. Centralino\n";
        text += "📞 095 7517910";
        return text;
    }
    
}
