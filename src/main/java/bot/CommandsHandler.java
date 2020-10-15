package bot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import commands.*;
import parser.ParserMenu;
import service.RegisterID;
import utils.MenuHelpers;

public class CommandsHandler extends TelegramLongPollingCommandBot {
    private List<IBotCommand> commands;

    public CommandsHandler(String botUsername) {
        super(botUsername);
        super.register(new ReportCommand()); // 0
        super.register(new MenuCommand()); // 1
        super.register(new UfficioErsuCommand()); // 2
        super.register(new StartCommand()); // 3
        super.register(new BanCommand()); // 5
        super.register(new UnbanCommand()); // 6
        super.register(new CloseCommand()); // 7
        super.register(new SendErrorCommand()); // 8
        super.register(new StatsCommand()); // 9
        commands = super.getRegisteredCommands().stream().collect(Collectors.toList());
    }
    
    @Override
    public void processInvalidCommandUpdate(Update update) {
        if (!update.hasMessage()) return;
        Message message = update.getMessage();
        RegisterID.write(message.getChatId().toString());
        
        SendMessage msg = new SendMessage()
                        .setChatId(message.getChatId());
        if (!message.getText().contains("@") && message.getChat().isUserChat()) {
            msg = msg
                    .setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup())
                    .setText("Questo comando non esiste. Scegli uno di questi comandi dal menù");
            try {
                execute(msg);
                return;
            } catch (TelegramApiException ex) {
             
            }
        }
        if (message.getText().contains("@")) {
            String command = selectCommandWithReplace(message.getText());
            key(message,command);
        }
            
        
    }
    private String selectCommandWithReplace(String msg) {
        return msg.split("@")[0].substring(1);
    }
    
    @Override
    public String getBotToken() {
        return YmlResolver.getInstance().getValue("token");
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().isUserMessage()) {
            Message message = update.getMessage(); 
            RegisterID.write(message.getChatId().toString());
            if (message.hasText()) {
                SendMessage msg = new SendMessage()
                        .setChatId(message.getChatId())
                        .setText("I comandi che puoi utilizzare sono quelli riportati in basso");
            }
            onKeyboardCommands(message);
            
        }
        
    }
    
    protected void onKeyboardCommands(Message message)  {
        SendMessage sndMsg = new SendMessage().setChatId(message.getChatId());
        if (message.isUserMessage()) {
            ReplyKeyboardMarkup rkm = MenuHelpers.generateMainMenuReplyKeyboardMarkup();

            sndMsg.setReplyMarkup(rkm);

            if (message.getText().equals("Contatti ERSU 📚")) {
                key(message, "ufficioersu");
                return; 
            }
            if (message.getText().equals("Menù mensa 🍽")) {
                key(message, "menu");
                return; 
            }
            if (message.getText().equals("Help ❔")) {
                key(message, "help");
                return; 
            }
            sndMsg.setText("Seleziona un comando dal menù\n");
            if (message.getText().equals("Segnalazioni Rappresentanti 📬")) 
                sndMsg.setText("Per inviare una richiesta o una segnalazione ai Rappresentanti ERSU scrivi il comando /report <inserisci qui la segnalazione>.");
    
            try { 
                execute(sndMsg);
            } catch (TelegramApiException ex) {
                //Logger 
            }
            
        }
    }

    private void key(Message message, String identifier) {
        Optional<IBotCommand> ufficioErsuCommand =
                commands.stream().filter(x -> x.getCommandIdentifier().equals(identifier)).findFirst();
        if (ufficioErsuCommand.isPresent())
            ufficioErsuCommand.get().processMessage(this, message, message.getText().split(" "));
    }
}
