package parser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParserMenu {
	private String text;
	public ParserMenu(File f) {
		try {
			text = new PdfExtracter(f).getText();
		} catch (IOException e) {
			e.printStackTrace();
			text = "";
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
	
	private String searchDay() {
		Map<String, String> dayMap = createMap();
		
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String today = dayMap.get(sdf.format(new Date()));
		String tomorrow = dayMap.get(sdf.format(new Date(new Date().getTime()+ 86400000L)));
		
		return text.split(today)[1].split(today)[0].split(tomorrow)[0];
	}
	
	private String[] getMenuToday() {
		String s = searchDay().split("CONTORNI")[2];
		String[] splitS = s.split("(?=\\p{Lu})");
		
		return splitS;
	}
	
	private String getPranzo() {
		String menuPranzo = "🍽MENÙ CENA: ";
		
		return menuPranzo;
	}
	
	
	/*public String getMenu() {
		
	}*/
}
