/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import bot.YmlResolver;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Pierpaolo
 */
public class UnbanCommandHandler extends CommandHandler {
    public UnbanCommandHandler() {
        super("unban");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();
        User user = update.getMessage().getFrom();

        if  (user.getId().toString().equals(chat.getId().toString())) {
            return; 
        }
        if (!chat.getId().toString().equals(YmlResolver.getInstance().getValue("admin_chat"))) {
            return;
        }
        
        if (parameters.length == 0) {
            noParam(bot, chat);
            return;
        }
        Integer id;
        try {
            id = Integer.parseInt(parameters[0]);
        } catch (Exception e) {
            noParam(bot, chat);
            return;
        }
        
        String text = "Utente non è bannato.";
        if (FileBanner.isPresent(""+id)) {
            FileBanner.remove(id.toString());
            text = "Utente {" + id + "} unbannato";
        }
        try {
            bot.execute(new SendMessage()
                    .setChatId(chat.getId())
                    .setText(text));

        } catch (TelegramApiException ex) {

        }

    }
    private void noParam(AbsSender as, Chat chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText("Comando unban errato oppure non hai inserito un ID utente. \n"
                        + "/ban ID_UTENTE");
        try {
            as.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(ReportCommandHandler.class).error("Errore invio FAILED segnalazione utente", ex);
        }
    }
}
