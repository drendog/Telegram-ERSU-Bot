package test;

import java.io.File;
import java.io.IOException;
import parser.PdfExtracter;
import parser.ParserMenu;

public class PdfExtracterTest {

    private PdfExtracter p;

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
        ParserMenu p = new ParserMenu(new File("data/menu.pdf"));
		//p.tokenizer();
        //System.out.println(p.searchDay());
        //p.getMenu();
        //p.getMenuToday();
        //System.out.println(p.getCena());
        System.out.println(p.getMenu());
    }
}
