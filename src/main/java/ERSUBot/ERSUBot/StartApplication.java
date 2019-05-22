package ERSUBot.ERSUBot;
import test.PdfExtracterTest;
import test.YmlResolverTest;

public class StartApplication {

	public static void main(String[] args) {
		YmlResolverTest t1 = new YmlResolverTest();
		PdfExtracterTest t2 = new PdfExtracterTest();
		t1.testToken();
		t1.testBotUsername();
		//t2.testExtracter();
		t2.testParser();
	}

}
