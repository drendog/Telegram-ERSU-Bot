package bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YmlResolver {
	private Yaml yml;
	private final String PATH = "/home/davide/eclipse-workspace/ERSUBot/src/config/settings.yml";
	public YmlResolver() {
		yml = new Yaml();
	}
	
	private FileInputStream getInputStream() {
		try {
			return new FileInputStream(PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
}
