package command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;

public class HelpCommand extends BotCommand { 

    public HelpCommand() {
        super("help", "Fornisce i comandi del bot.");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        String text = "<b>Telegram ERSU Bot</b>\n\n"+
                    "🍽 /menu Fornisce il menù per il prossimo pasto Mensa;\n\n"+
                    "📚 /ufficioersu Fornisce informazioni sugli uffici ERSU Catania;\n\n"+
                    "📬 /report Fornisce la possibilità di poter inviare una segnalazione ai "
                    + "Rappresentanti ERSU, riguardante qualsiasi disservizio, informazione, dubbi e domande.";
        
        SendMessage message = new SendMessage()
                        .setChatId(chat.getId().toString())
                        .enableHtml(true)
                        .setText(text);
        RegisterID.write(chat.getId().toString());
        if (chat.isUserChat()) message.setReplyMarkup(bot.Bot.generateRKM());
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(MenuCommand.class).error("Errore invio comando menu", ex);
        }
    }
    
}