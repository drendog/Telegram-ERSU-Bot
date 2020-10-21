package bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import commands.BanCommandHandler;
import commands.CloseCommandHandler;
import commands.CommandHandler;
import commands.HelpCommandHandler;
import commands.MenuCommandHandler;
import commands.ReportCommandHandler;
import commands.SendErrorCommandHandler;
import commands.StartCommandHandler;
import commands.StatsCommandHandler;
import commands.UfficioErsuCommandHandler;
import commands.UnbanCommandHandler;
import messages.IMessageHandler;
import messages.KeyboardCommandHandler;
import utils.ResourcesHelpers;
import messages.AdminReplyMessageHandler;

public class Bot extends TelegramLongPollingBot {
    protected List<IMessageHandler> messageHandlers;
    protected List<CommandHandler> commandHandlers;

    public Bot() {
        messageHandlers = new ArrayList<>();
        commandHandlers = new ArrayList<>();

        messageHandlers.add(new AdminReplyMessageHandler());
        messageHandlers.add(new KeyboardCommandHandler("Contatti ERSU 📚", ResourcesHelpers.loadTextReply("ufficioersu")));
        messageHandlers.add(new KeyboardCommandHandler("Help ❔", ResourcesHelpers.loadTextReply("help")));
        messageHandlers.add(new KeyboardCommandHandler("Segnalazioni Rappresentanti 📬", ResourcesHelpers.loadTextReply("segnalazioni_rappresentanti")));
        messageHandlers.add(new KeyboardCommandHandler("Menù mensa 🍽", ResourcesHelpers.loadTextReply("menu_mensa_disabled")));

        commandHandlers.add(new BanCommandHandler());
        commandHandlers.add(new CloseCommandHandler());
        commandHandlers.add(new HelpCommandHandler());
        commandHandlers.add(new MenuCommandHandler());
        commandHandlers.add(new ReportCommandHandler());
        commandHandlers.add(new SendErrorCommandHandler());
        commandHandlers.add(new StartCommandHandler());
        commandHandlers.add(new StatsCommandHandler());
        commandHandlers.add(new UfficioErsuCommandHandler());
        commandHandlers.add(new UnbanCommandHandler());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();

            messageHandlers.stream()
                .filter(handler -> handler.shouldHandle(message))
                .forEach(handler -> handler.handleRequest(this, update));

            if(message.isCommand()) {
                commandHandlers.stream()
                    .filter(handler -> handler.shouldHandle(message))
                    .findFirst().get().handleRequest(this, update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return YmlResolver.getInstance().getValue("BotUsername");
    }

    @Override
    public String getBotToken() {
        return YmlResolver.getInstance().getValue("token");
    }
    
}
