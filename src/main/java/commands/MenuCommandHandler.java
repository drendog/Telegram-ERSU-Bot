
package commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.ParserMenu;
import service.RegisterID;
import utils.MenuHelpers;

public class MenuCommandHandler extends CommandHandler { 
    public MenuCommandHandler() {
        super("menu");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        SendMessage message = new SendMessage()
                        .setChatId(chat.getId().toString())
                        .setParseMode(ParseMode.HTML)
                        .setText(ParserMenu.getInstance().getMenu());
        if (chat.isUserChat()) message.setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());
        RegisterID.write(chat.getId().toString());
        try {
            bot.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(MenuCommandHandler.class).error("Errore invio comando menu", ex);
        }
    }
    
}
