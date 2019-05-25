package parser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParserMenu {

    private String text;

    public ParserMenu(File f) {
        text = new PdfExtracter(f).getText();
    }

    private Map<String, String> createMap() {
        Map<String, String> dayMap = new HashMap<String, String>();

        dayMap.put("lun", "LUNEDI’");
        dayMap.put("mar", "MARTEDI’");
        dayMap.put("mer", "MERCOLEDI’");
        dayMap.put("gio", "GIOVEDI’");
        dayMap.put("ven", "VENERDI’");
        dayMap.put("sab", "SABATO");
        dayMap.put("dom", "DOMENICA");

        return dayMap;
    }

    private String searchDay() {
        Map<String, String> dayMap = createMap();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String today = dayMap.get(sdf.format(new Date()));
        String tomorrow = dayMap.get(sdf.format(new Date(new Date().getTime() + 86400000L)));

        return text.split(today)[1].split(today)[0].split(tomorrow)[0];
    }

    public String[] getMenuToday() {
        String s = searchDay().split("CONTORNI")[2];
        String[] splitS = s.split("(?=\\p{Lu})");

        return splitS;
    }

    private String getPranzo() {
        String menuPranzo = "🍽MENÙ CENA: \n";
        String[] piatti = getMenuToday();

        //Primi Piatti
        menuPranzo += "PRIMI PIATTI\n";
        menuPranzo += piatti[1] + "\n";
        menuPranzo += piatti[7] + "\n";
        menuPranzo += piatti[13] + "\n";
        menuPranzo += piatti[19] + "\n";

        //Secondi Piatti
        menuPranzo += "\nSECONDI PIATTI\n";
        menuPranzo += piatti[2] + "\n";
        menuPranzo += piatti[8] + "\n";
        menuPranzo += piatti[14] + "\n";
        menuPranzo += piatti[20] + "\n";
        menuPranzo += piatti[26] + "\n";

        //Contorni
        menuPranzo += "CONTORNI\n";
        menuPranzo += piatti[3] + "\n";
        menuPranzo += piatti[9] + "\n";
        menuPranzo += piatti[16] + "\n";
        menuPranzo += piatti[21] + "\n";

        return menuPranzo;
    }

    private String getCena() {
        String menuPranzo = "🍽MENÙ CENA: \n";
        String[] piatti = getMenuToday();

        //Primi Piatti
        menuPranzo += "PRIMI PIATTI\n";
        menuPranzo += piatti[4] + "\n";
        menuPranzo += piatti[10] + "\n";
        menuPranzo += piatti[16] + "\n";
        menuPranzo += piatti[22] + "\n";

        //Secondi Piatti
        menuPranzo += "\nSECONDI PIATTI\n";
        menuPranzo += piatti[5] + "\n";
        menuPranzo += piatti[11] + "\n";
        menuPranzo += piatti[17] + "\n";
        menuPranzo += piatti[23] + "\n";
        menuPranzo += piatti[26] + "\n";

        //Contorni
        menuPranzo += "CONTORNI\n";
        menuPranzo += piatti[3] + "\n";
        menuPranzo += piatti[9] + "\n";
        menuPranzo += piatti[16] + "\n";
        menuPranzo += piatti[21] + "\n";

        return menuPranzo;
    }

    public String getMenu() {
        if (Calendar.getInstance().get(Calendar.HOUR) < 15) {
            return getPranzo();
        }
        return getCena();
    }
}
