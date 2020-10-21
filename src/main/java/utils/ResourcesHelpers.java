package utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class ResourcesHelpers {
    public static String loadTextReply(String id) {
        String resourcePath = "../resources/text/" + id + ".html";

        File file = new File(resourcePath);

        String text;
        try {
            text = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();

            Logger.getLogger(ResourcesHelpers.class).error("Unable to load text reply " + id + ":" + e);
            return null;
        }

        return text;
    }
}
