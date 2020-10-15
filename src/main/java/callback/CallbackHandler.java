package callback;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class CallbackHandler {
   protected String handlerKey;
   protected CallbackHandler(String handlerKey) {
      this.handlerKey = handlerKey;
   }
   
   public boolean shouldHandle(String requestKey) {
      boolean result = requestKey.matches(handlerKey);

      return result;
   }

   public abstract void handleRequest(AbsSender bot, Update update);
}
