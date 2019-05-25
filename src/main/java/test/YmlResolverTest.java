package test;

import bot.YmlResolver;

public class YmlResolverTest {

    public void testToken() {
        if (YmlResolver.getInstance().getValue("token").equals("pippo")) {
            System.out.println("OK - Token");
        } else {
            System.out.println("FAILED - Token");
        }

    }

    public void testPathAvvisi() {
        if (YmlResolver.getInstance().getValue("path_avvisi").equals("pippo")) {
            System.out.println("OK - Path Avvisi");
        } else {
            System.out.println("FAILED - Path Avvisi");
        }
    }

    public void testBotUsername() {
        if (YmlResolver.getInstance().getValue("BotUsername").equals("ERSUBot")) {
            System.out.println("OK - BotUsername");
        } else {
            System.out.println("FAILED - BotUsername");
        }

    }
}
