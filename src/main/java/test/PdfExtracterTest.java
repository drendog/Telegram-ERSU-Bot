package test;

import java.io.File;
import parser.PdfExtracter;
import parser.ParserMenu;

public class PdfExtracterTest {

    private PdfExtracter p;

    public PdfExtracterTest() {
        p = new PdfExtracter(new File("data/menu.pdf"));
    }

    public void testExtracter() {
        
            System.out.println(p.getText());
        
    }

    public void testParser() {
        ParserMenu p = ParserMenu.getInstance();
		//p.tokenizer();
        //System.out.println(p.searchDay());
        //p.getMenu();
        //p.getMenuToday();
        //System.out.println(p.getCena());
        System.out.println(p.getMenu());
    }
}
