package callback;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ReportReplyCallback extends CallbackHandler {
    protected Logger logger;

    public ReportReplyCallback() {
        super("report_reply");

        logger = Logger.getLogger(this.getClass());
    }

    @Override
    public void handleRequest(AbsSender bot, Update update) {
        // Unused
    }
}
