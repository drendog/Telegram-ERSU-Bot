import java.io.File;
import java.io.IOException;

import parser.ParserMenu;
import parser.PdfExtracter;

public class PdfExtracterTest {
	PdfExtracter p;
	public PdfExtracterTest() {
		p = new PdfExtracter(new File("data/menu.pdf"));
	}
	
	public void testExtracter() {
		try {
			System.out.println(p.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testParser() {
		ParserMenu p = new ParserMenu(new File("data/menu.pdf")) ;
		//p.tokenizer();
		//System.out.println(p.searchDay());
		//p.getMenu();
		p.getMenuToday();
		System.out.println(p.getPranzo());
	}
}
