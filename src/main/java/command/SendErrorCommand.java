package command;

import bot.*;
import utils.MenuHelpers;

import java.io.File;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendErrorCommand extends BotCommand{

    public SendErrorCommand() {
        super("error","Invia il log degli errori");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat")))
                return;
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chat.getId().toString())
                .setDocument(new File(YmlResolver.getInstance().getValue("log4j.properties")))
                .setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());
        try {
            as.execute(sendDocument);
        } catch (TelegramApiException ex) {
            
        }
    }
    
}
