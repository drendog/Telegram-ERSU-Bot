package messages;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

import utils.MenuHelpers;

public class KeyboardCommandHandler implements IMessageHandler {
    protected String messageKey;
    protected String replyText;

    protected Logger logger;

    public KeyboardCommandHandler(String messageKey, String replyText) {
        this.messageKey = messageKey;
        this.replyText = replyText;

        this.logger = Logger.getLogger(this.messageKey + "KeyboardCommandHandler");
    }

    @Override
    public boolean shouldHandle(Message message) {
        return message.getText().equals(this.messageKey);
    }

    @Override
    public void handleRequest(AbsSender bot, Update update) {
        SendMessage sendReplyRequest = new SendMessage()
            .setChatId(update.getMessage().getChatId())
            .setText(this.replyText)
            .enableHtml(true)
            .setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());

        try {
            bot.execute(sendReplyRequest);
        } catch(Exception e) {
            this.logger.error("Unable to send reply to update: " + update + ":" + e);
        }
    }
    
}
