package bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YmlResolver {

    private final Yaml yml;
    private final String PATH = "src/config/settings.yml";
    private static final YmlResolver instance = new YmlResolver();

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

    private Map<String, Object> getMap() {
        return yml.load(getInputStream());
    }

    public String getValue(String key) {
        return (String) getMap().get(key);
    }

}
