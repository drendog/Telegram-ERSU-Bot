package command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelpCommand extends BotCommand { 

    public HelpCommand() {
        super("help", "Fornisce i comandi del bot.");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        String text = "Telegram ERSU Bot\n"+
                    "/menu fornisce il menù per il prossimo pasto\n"+
                    "/ufficioersu fornisce informazioni sugli uffici ERSU\n"+
                    "/report fornisce un servizio di segnalazione di problemi"+
                    " relativi a tutto ciò che concerne l'ERSU";
        SendMessage message = new SendMessage()
                        .setChatId(chat.getId().toString())
                        .setParseMode(ParseMode.HTML)
                        .setText(text);
        if (chat.isUserChat()) message.setReplyMarkup(bot.Bot.generateRKM());
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(MenuCommand.class).error("Errore invio comando menu", ex);
        }
    }
    
}