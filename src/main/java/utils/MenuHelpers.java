package utils;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class MenuHelpers {
    public static ReplyKeyboardMarkup generateMainMenuReplyKeyboardMarkup() {
        ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
        List<KeyboardRow> commands = new ArrayList<KeyboardRow>(); 
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("Menù mensa 🍽");
        firstRow.add("Contatti ERSU 📚");
        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("Segnalazioni Rappresentanti 📬");
        secondRow.add("Help ❔"); 
        commands.add(firstRow);
        commands.add(secondRow);
        rkm.setResizeKeyboard(true);
        rkm.setOneTimeKeyboard(true);
        rkm.setKeyboard(commands);
        rkm.setSelective(true);
        return rkm; 
    }
}
