package parser;

import bot.YmlResolver;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParserMenu {

    private String text;
    private SimpleDateFormat format;

    public ParserMenu(File f) {
        if (f.exists()) {
            text = new PdfExtracter(f).getText();
            format = new SimpleDateFormat("dd.MM.yyyy");
        }
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

    public Date getStartDateMenu() {
        String text = this.text.toUpperCase();
        String tmp = text.split("DAL")[1].replace("AL", "").replace("PRANZO", "").split(" ")[1];

        try {
            Date start = format.parse(tmp);
            return start;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Date getEndDateMenu() {
        String text = this.text.toUpperCase();
        String tmp = text.split("DAL")[1].replace("AL", "").replace("PRANZO", "").split(" ")[3];
        
        try {
            Date end = format.parse(tmp);
            return end;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
        String menuPranzo = "<b>🍽 MENÙ PRANZO:</b> \n";
        String[] piatti = getMenuToday();

        //Primi Piatti
        menuPranzo += "<b>PRIMI PIATTI</b>\n";
        menuPranzo += piatti[1] + "\n";
        menuPranzo += piatti[8] + "\n";
        menuPranzo += piatti[14] + "\n";
        menuPranzo += piatti[20] + "\n";

        //Secondi Piatti
        menuPranzo += "\n<b>SECONDI PIATTI</b>\n";
        menuPranzo += piatti[2] + "\n";
        menuPranzo += piatti[9] + "\n";
        menuPranzo += piatti[15] + "\n";
        menuPranzo += piatti[21] + "\n";
        menuPranzo += piatti[26] + "\n";

        //Contorni
        menuPranzo += "<b>CONTORNI</b>\n";
        menuPranzo += piatti[3] + "\n";
        menuPranzo += piatti[10] + "\n";
        menuPranzo += piatti[16] + "\n";
        menuPranzo += piatti[22] + "\n";

        return menuPranzo;
    }

    private String getCena() {
        String menuPranzo = "<b>🍽 MENÙ CENA:</b> \n";
        String[] piatti = getMenuToday();

        //Primi Piatti
        menuPranzo += "<b>PRIMI PIATTI</b>\n";
        menuPranzo += piatti[4] + "\n";
        menuPranzo += piatti[10] + "\n";
        menuPranzo += piatti[16] + "\n";
        menuPranzo += piatti[22] + "\n";

        //Secondi Piatti
        menuPranzo += "\n<b>SECONDI PIATTI</b>\n";
        menuPranzo += piatti[5] + "\n";
        menuPranzo += piatti[11] + "\n";
        menuPranzo += piatti[17] + "\n";
        menuPranzo += piatti[23] + "\n";
        menuPranzo += piatti[26] + "\n";

        //Contorni
        menuPranzo += "<b>CONTORNI</b>\n";
        menuPranzo += piatti[3] + "\n";
        menuPranzo += piatti[9] + "\n";
        menuPranzo += piatti[16] + "\n";
        menuPranzo += piatti[21] + "\n";

        return menuPranzo;
    }

    private boolean menuDateisOk(){
        Date now = new Date(System.currentTimeMillis());

        if(now.after(getStartDateMenu()) && now.before(getEndDateMenu()))
            return true;
        
        return false;
    }
    
    public static boolean isOkDate() {
       return new ParserMenu( 
               new File(YmlResolver.getInstance().getValue("path_mensa")) 
               ).menuDateisOk();
    }
    public static boolean isOkFile() {
        return new File( YmlResolver.getInstance().getValue("path_mensa") 
                ).exists();
    }
    public String getMenu() {
        if(menuDateisOk()){
            if (Calendar.getInstance().get(Calendar.HOUR) < 15){
                return getPranzo();
            }
            else{
                return getCena();
            }
        }
        else
            return "Menù mensa non disponibile perché non ancora pubblicato sul Sito ERSU Catania.";
    }
}
