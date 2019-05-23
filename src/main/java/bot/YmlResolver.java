package bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YmlResolver {
	private Yaml yml;
	private final String PATH = "src/config/settings.yml";
	private static YmlResolver instance = new YmlResolver();
	
	private YmlResolver() {
		yml = new Yaml();
	}
	
	public static YmlResolver getInstance() {
		return instance;
	}
	
	private FileInputStream getInputStream() {
		try {
			return new FileInputStream(PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Map<String, Object> getMap(){
		return yml.load(getInputStream());
	}
	
	public String getToken() {
		return (String) getMap().get("token");
	}
	
	public String getBotUsername() {
		return (String) getMap().get("BotUsername");
	}
	
	public String getChannelUsername() {
		return (String) getMap().get("mensa_channel");
	}
}
