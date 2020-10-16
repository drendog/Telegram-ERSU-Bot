package commands;

import java.io.File;

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

public class HelpCommandHandler extends CommandHandler {
    public HelpCommandHandler() {
        super("help");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        String text = ResourcesHelpers.loadTextReply("help");

        SendMessage message = new SendMessage().setChatId(chat.getId().toString()).enableHtml(true).setText(text);
        RegisterID.write(chat.getId().toString());
        if (chat.isUserChat())
            message.setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());
        try {
            bot.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(MenuCommandHandler.class).error("Errore invio comando menu", ex);
        }
    }
}