package commands;

import bot.*;
import utils.MenuHelpers;

import java.io.File;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendErrorCommandHandler extends CommandHandler {
    public SendErrorCommandHandler() {
        super("error");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat")))
                return;
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chat.getId().toString())
                .setDocument(new File(YmlResolver.getInstance().getValue("log4j.properties")))
                .setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());
        try {
            bot.execute(sendDocument);
        } catch (TelegramApiException ex) {
            
        }
    }
    
}
