import java.io.File;
import java.io.IOException;

import scraper.PdfExtracter;

public class PdfExtracterTest {
	PdfExtracter p;
	public PdfExtracterTest() {
		p = new PdfExtracter(new File("data/menu.pdf"));
	}
	
	public void testExtracter() {
		try {
			p.printLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
