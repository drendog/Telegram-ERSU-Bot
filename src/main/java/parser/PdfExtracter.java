package parser;

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
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore file PDF menu con password", e);
        } catch (IOException e) {
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore apertura file PDF menu", e);
        }
    }

    public String getText() {
        PDFTextStripperByArea pdfTextStripperByArea = null;
        try {
            pdfTextStripperByArea = new PDFTextStripperByArea();
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore PDFTextStripperByArea", ex);
        }
        pdfTextStripperByArea.setSortByPosition(Boolean.TRUE);

        
        PDFTextStripper pdfTextStripper = null;
        try {
            pdfTextStripper = new PDFTextStripper();
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore PDFTextStripper", ex);
        }
        
        String text = null;
        try {
            text = pdfTextStripper.getText(pdf);
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore get text pdf menu", ex);
        }
        try {
            pdf.close();
        } catch (IOException ex) {
            org.apache.log4j.Logger.getLogger(PdfExtracter.class).error("Errore chiusura file PDF menu", ex);
        }
        return text;
    }
}
