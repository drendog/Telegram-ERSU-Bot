package messages;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface IMessageHandler {
   public boolean shouldHandle(Message message);
   public void handleRequest(AbsSender bot, Update update);
}
