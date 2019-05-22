package scraper;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;


public class PdfExtracter {
	private PDDocument pdf;
	
	public PdfExtracter(File f) {
		try {
			pdf = PDDocument.load(f);
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getText() throws IOException{
        PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
        pdfTextStripperByArea.setSortByPosition(Boolean.TRUE);

        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        return pdfTextStripper.getText(pdf);
	}
	
	private String[] SplitText() throws IOException{
		return getText().split("\\r?\\n");
	}
	
	public void printLines() throws IOException{
		System.out.println(getText());
	}
}
