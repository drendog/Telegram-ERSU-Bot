import bot.YmlResolver;

public class YmlResolverTest {

	YmlResolver y;
	
	public YmlResolverTest() {
		y = new YmlResolver();
	}

	public void testToken() {
		if(y.getToken().equals("pippo"))
			System.out.println("OK - Token");
		else
			System.out.println("FAILED - Token");
		
	}
	
	public void testBotUsername() {
		if(y.getBotUsername().equals("ERSUBot"))
			System.out.println("OK - BotUsername");
		else
			System.out.println("FAILED - BotUsername");
		
	}
}
