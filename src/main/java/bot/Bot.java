package bot;

import command.MenuCommand;
import command.ReportCommand;
import command.StartCommand;
import command.UfficioErsuCommand;
import java.io.File;
import org.apache.log4j.Logger;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import parser.ParserMenu;

public class Bot extends TelegramLongPollingCommandBot {

    private final String PATH = YmlResolver.getInstance().getValue("path_mensa");
    private ParserMenu p = new ParserMenu(new File(PATH));

    public Bot(String botUsername) {
        super(botUsername);
        super.register(new ReportCommand());
        super.register(new MenuCommand(p));
        super.register(new UfficioErsuCommand());
        super.register(new StartCommand());
    }

    @Override
    public String getBotToken() {
        return YmlResolver.getInstance().getValue("token");
    }

    private void sendMessageToChannel(String message){ 
        SendMessage sd = new SendMessage();
        sd.enableMarkdown(true);
        sd.setChatId(YmlResolver.getInstance().getValue("mensa_channel"));

        sd.setText(message);
        sd.enableMarkdown(true);

        try {
            execute(sd);
        } catch (TelegramApiException ex) {
            Logger.getLogger(Bot.class).error("Errore invio messaggio al canale",ex);
        }
    }

    public void sendMenuToChannel() {
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
                    Logger.getLogger(Bot.class).error("Errore invio messaggio");
                }
            }
        }
    }
}
