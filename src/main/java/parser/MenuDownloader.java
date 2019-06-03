package parser;

import bot.YmlResolver;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MenuDownloader extends TimerTask  {
    private static MenuDownloader istance; 

    private MenuDownloader() {
        
    }
    
    public static MenuDownloader getDownloader() {
        if (istance == null)
            istance = new MenuDownloader();
        return istance; 
    }
    
    @Override
    public void run() {
        if (ParserMenu.getInstance().isOkFile() && ParserMenu.getInstance().isOkDate() ) return;
        download(); // else.  
    }
    
    
    public static void download() {
        ReadableByteChannel readableByteChannel = null;
        try {
            readableByteChannel = Channels.newChannel(getLinkPdf().openStream());
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(YmlResolver.getInstance().getValue("path_mensa"));
            fileOutputStream.getChannel().transferFrom(readableByteChannel, fileOutputStream.getChannel().position(), Long.MAX_VALUE);
            fileOutputStream.close();
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore DOWNLOAD file PDF menu", ex);
        }
    }
    
    private static URL getLinkPdf() {
        Document doc = getDocument();
        URL url = null;
        try {
            url = new URL(doc.getElementsByClass("entry-content clearfix").get(0).children().get(0).children().attr("href"));
        } catch (MalformedURLException ex) {
            org.apache.log4j.Logger.getLogger(MenuDownloader.class).error("Errore link PDF menu", ex);
        }
        return url; 
    }

    private static Document getDocument() {
        Document doc = null;
        try {
            doc =  Jsoup.connect("http://www.ersucatania.gov.it/menu-mensa/").get();
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(MenuDownloader.class).error("Errore connessione link PDF menu", ex);
        }
        return doc;
    }

    
}
