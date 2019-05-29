package command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand { 

    public StartCommand() {
        super("start", "Fornisce delle informazioni sul bot.");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        String text = "Benvenuto! Questo bot è stato realizzato dagli studenti del Corso di Laurea"+ 
                    " in Informatica al fine di fornire uno strumento di supporto per chi"+
                    " usufruisce dei servizi ERSU. Per scoprire cosa puoi"+ 
                    " fare usa /help";
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