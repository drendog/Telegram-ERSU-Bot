package bot;

import command.MenuCommand;
import command.ReportCommand;
import java.io.File;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import parser.ParserMenu;

public class Bot extends TelegramLongPollingCommandBot {

    private final String PATH = "data/menu.pdf";
    private ParserMenu p = new ParserMenu(new File(PATH));

    public Bot(String botUsername) {
        super(botUsername);
        super.register(new ReportCommand());
        super.register(new MenuCommand(p));
    }

    
    /*
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().contains("/menu")) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(p.getMenu());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
    /*
    public String getBotUsername() {
        return YmlResolver.getInstance().getValue("BotUsername");
    }
    */

    @Override
    public String getBotToken() {
        return YmlResolver.getInstance().getValue("token");
    }

    private void sendMessageToChannel(String message) throws TelegramApiException {
        SendMessage sd = new SendMessage();
        sd.enableMarkdown(true);
        sd.setChatId(YmlResolver.getInstance().getValue("mensa_channel"));

        sd.setText(message);
        sd.enableMarkdown(true);

        execute(sd);
    }

    public void sendMenuToChannel() throws TelegramApiException {
        sendMessageToChannel(p.getMenu());
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage()
                .setChatId(message.getChatId())
                .setText("Questo messaggio non mi comanda nulla: \n" + message.getText());

                try {
                    execute(echoMessage);
                } catch (TelegramApiException e) {
                }
            }
        }
    }
}
