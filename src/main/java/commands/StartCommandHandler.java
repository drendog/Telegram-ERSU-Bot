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

public class StartCommandHandler extends CommandHandler { 
    public StartCommandHandler() {
        super("start");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        System.out.println("Loading text...");

        String text = ResourcesHelpers.loadTextReply("start");

        System.out.println(text);

        SendMessage message = new SendMessage()
                        .setChatId(chat.getId().toString())
                        .setParseMode(ParseMode.HTML)
                        .setText(text);

        if (chat.isUserChat())
            message.setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());

        RegisterID.write(chat.getId().toString());

        try {
            RegisterID.write(chat.getId().toString());
            bot.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(StartCommandHandler.class).error("Errore invio comando menu", ex);
        }
    }
    
}