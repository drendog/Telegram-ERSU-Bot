package test;
import bot.YmlResolver;

public class YmlResolverTest {

	public void testToken() {
		if(YmlResolver.getInstance().getToken().equals("pippo"))
			System.out.println("OK - Token");
		else
			System.out.println("FAILED - Token");
		
	}
	
	public void testBotUsername() {
		if(YmlResolver.getInstance().getBotUsername().equals("ERSUBot"))
			System.out.println("OK - BotUsername");
		else
			System.out.println("FAILED - BotUsername");
		
	}
}
