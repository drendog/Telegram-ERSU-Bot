package parser;

import bot.Bot;
import bot.YmlResolver;
import java.io.IOException;
import java.util.TimerTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Scraper extends TimerTask {

    private final static String URL =  YmlResolver.getInstance().getValue("url_ersu");
    
    private final static String NEWS_CHANNEL = YmlResolver.getInstance().getValue("news_channel");
    private Document doc;
    private Bot bot;

    public Scraper(Bot bot) {
        this.bot = bot;
    }

    public void checkNews() throws TelegramApiException {
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(Scraper.class).error("Connessione host ERSU non riuscita", ex);
        }
        final String content = FileManager.read(); // Deve 
        Elements elements = doc.getElementsByClass("half");
        for (Element e1 : elements) {
            String link = e1.child(0).attributes().get("href");
            if (!content.contains(link)) {
                bot.execute(
                        new SendMessage()
                        .enableHtml(true)
                        .setChatId(NEWS_CHANNEL)
                        .setText(getNews(link))
                );
            }
        }
    }

    private String getNews(String link) {
        FileManager.write(link);
        Document news = null;
        try {
            news = Jsoup.connect(link).get();
        } catch (IOException ex) {
            // Logging
        }
        if (news == null) {
            return "Errore creazione news";
        }
        
        return getContentNews(news);

    }

    private String getContentNews(Document doc) {
        String section = doc.getElementsByAttributeValueContaining("property", "article:section").attr("content");
        String title = doc.getElementsByAttributeValueContaining("property", "og:title").attr("content");
        String description = doc.getElementsByAttributeValueContaining("property", "og:description").attr("content");
        String url = doc.getElementsByAttributeValueContaining("property", "og:url").attr("content");

        String news = "<b>[" + section + "]</b>" + "\n"
                + url + "\n\n"
                + "<b>" + title + "</b>" + "\n"
                + description;

        return news;
    }

    @Override
    public void run() {
        try {
            checkNews();
        } catch (TelegramApiException ex) {
            org.apache.log4j.Logger.getLogger(Scraper.class).error("Errore Check News", ex);
        }
    }

}
