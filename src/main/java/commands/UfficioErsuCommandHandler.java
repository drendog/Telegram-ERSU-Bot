
package commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.RegisterID;
import utils.MenuHelpers;

public class UfficioErsuCommandHandler extends CommandHandler { 
    public UfficioErsuCommandHandler() {
        super("ufficioersu");
    }

    @Override
    public void handleRequest(AbsSender bot, Update update, String[] parameters) {
        Chat chat = update.getMessage().getChat();

        String text = getText();
        SendMessage message = new SendMessage()
                        .setParseMode(ParseMode.HTML)
                        .setChatId(chat.getId().toString())
                        .setText(text);
        if (chat.isUserChat()) message.setReplyMarkup(MenuHelpers.generateMainMenuReplyKeyboardMarkup());
        try {
            RegisterID.write(chat.getId().toString());
            bot.execute(message);
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(UfficioErsuCommandHandler.class).error("Errore invio comando ufficioersu", ex);
        }
    }

    public String getText() {
        String text = "<b>ERSU CATANIA</b>\n\n"
        +"📍 Via Etnea, 570\n\n"
        +"🕑 Orari di ricevimento\n"
        +"Lunedì 09:00 - 12:00\n"
        +"Mercoledì 15:00 - 18:00\n"
        +"Venerdì 09:00 - 12:00\n\n"

        +"☎️ Contatti\n"
        +"• CENTRALINO\n"
        +"📞 095 7517910\n\n"

        +"• URP\n"
        +"📞 095 7517940\n\n"

        +"• ASSEGNAZIONE, BORSE DI STUDIO\n"
        +"📞 0957517935\n"
        +"📞 0957517932\n"
        +"📧 assegnazione@ersucatania.it \n"
        +"L'ufficio risponde nei seguenti orari\n"
        +"Lunedì, Mercoledì, Venerdì ore 8.00-9.00 e 13.00-14.00; \n"
        +"Martedì e Giovedì ore 8.00-10.00 e 13.00-14.00.\n\n"

        +"• RISTORAZIONE\n"
        +"📞 097517955\n"
        +"📧 ristorazione@ersucatania.it\n\n"

        +"• ATTIVITA' CULTURALI\n"
        +"📞 097517968 \n"
        +"📧 attivitaculturali@ersucatania.it \n\n"

        +"• TRASPORTI EXTRAURBANI E ABBONAMENTI TEATRALI \n"
        +"📞 0957517913\n"
        +"📧 trasportiextraurbani@ersucatania.it\n\n"

        +"• ERASMUS INCOMING E FORESTERIA  \n"
        +"📞 0957517937  \n"
        +"📧 erasmus@ersucatania.it\n"
        +"📧 foresteria@ersucatania.it \n\n"

        +"• COORDINAMENTO CASE \n"
        +"📧 residenze@ersucatania.it\n\n"

        +"• RESIDENZA CITTADELLA\n"
        +"📞 095330911\n"
        +"📧 residenza.cittadella@ersucatania.it\n\n"

        +"• RESIDENZA CENTRO\n"
        +"📞 095504680\n"
        +"📧 residenza.centro@ersucatania.it\n\n"

        +"• RESIDENZA TOSCANO SCUDERI\n"
        +"📞 095436481\n\n"

        +"• RESIDENZA VIA VERONA\n"
        +"📞 0957167107\n\n"

        +"• RESIDENZA SAN MARZANO\n"
        +"📞 095330868\n";
        return text;
    }
    
}
