
package commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;
import utils.MenuHelpers;
import utils.ResourcesHelpers;

public class UfficioErsuCommandHandler extends CommandHandler { 
    public UfficioErsuCommandHandler() {
        super("ufficioersu");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        String text = ResourcesHelpers.loadTextReply("ufficioersu");

        SendMessage message = new SendMessage()
                        .setParseMode(ParseMode.HTML)
                        .setChatId(chat.getId().toString())
                        .setText(text);
        if (chat.isUserChat()) message.setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());
        try {
            RegisterID.write(chat.getId().toString());
            bot.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(UfficioErsuCommandHandler.class).error("Errore invio comando ufficioersu", ex);
        }
    }
}
