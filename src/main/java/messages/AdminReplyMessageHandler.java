package messages;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot.YmlResolver;
import service.ReportsRegistry;

public class AdminReplyMessageHandler implements IMessageHandler {
    protected Logger logger;

    public AdminReplyMessageHandler() {
        logger = Logger.getLogger(this.getClass());
    }

    @Override
    public void handleRequest(AbsSender bot, Update update) {
        logger.info("Handling admin reply message");

        Message adminReplyMessage = update.getMessage();
        Message reportMessage = adminReplyMessage.getReplyToMessage();

        Optional<Long> authorChatIdBox = ReportsRegistry.getInstance().getAuthorOfReport(reportMessage.getMessageId());

        if (authorChatIdBox.isEmpty()) {
            logger.warn("Reply to untracked report " + reportMessage.getMessageId());
            return;
        }

        long authorChatId = authorChatIdBox.get();

        SendMessage sendReplyToUserRequest = new SendMessage()
                .setChatId(authorChatId)
                .setText("In risposta alla tua richiesta: " + adminReplyMessage.getText());

        try {
            bot.execute(sendReplyToUserRequest);
        } catch (TelegramApiException e) {
            logger.error("Errore durante l'invio della risposta all'utente" + authorChatId + ": " + e);
        }

        SendMessage adminNotificationRequest = new SendMessage()
            .setChatId(adminReplyMessage.getChatId())
            .setText("La tua risposta è stata inoltrata all'utente '" + authorChatId + "'");

        try {
            bot.execute(adminNotificationRequest);
        } catch (TelegramApiException e) {
            logger.error("Errore durante l'invio della notifica di risposta agli amministratori");
        }
    }

    @Override
    public boolean shouldHandle(Message message) {
        if(!message.isReply())
            return false;

        try {
            long adminChatId = Long.parseLong(YmlResolver.getInstance().getValue("admin_chat"));

            if(message.getChatId() != adminChatId)
                return false;
        } catch(NumberFormatException e) {
            logger.error("Invalid configuration value 'admin_chat'");
            return false;
        }

        return true;
    }
}
