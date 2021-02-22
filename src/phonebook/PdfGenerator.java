/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

/**
 *
 * @author Krisztián
 */
public class PdfGenerator {
    
    public void pdfGeneration(String fileName, ObservableList<Person> data){
        Document doc = new Document();
        
        try {
            //Kiírás fájlba
            PdfWriter.getInstance(doc, new FileOutputStream(fileName + ".pdf"));
            doc.open();
            
            //Céges logó a PDF tetején
            Image image1 = Image.getInstance(getClass().getResource("/logo.png"));
            image1.scaleToFit(200, 86);
                                     //jobbra,le
            image1.setAbsolutePosition(250f, 720f);
            doc.add(image1);
            
            //sortörés
            doc.add(new Paragraph("\n\n\n\n\n\n\n\n"));
            
            //Tartalom 
            //Sima szöveg tartalom hozzáadása
            //doc.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n" + text, 
                    //FontFactory.getFont("betutipus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    
            //táblázat készítése
            float[] columnWidths = {3, 4, 4, 6};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Kontaktlista"));
            cell.setBackgroundColor(GrayColor.GRAY);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("Email");
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.3f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for(int i = 1; i <= data.size(); i++){
                Person actualPerson = data.get( i - 1 );
                
                table.addCell(""+i);
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getEmail());
                
            }
            
            doc.add(table);
            
            
            
            
            //Céges aláírás a PDF alján
            Chunk signo = new Chunk("\n\n\n A telefonkönyv alkalmazás által generált pdf dokumentum.");
            Paragraph standard = new Paragraph(signo);
            
            doc.add(standard);
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();
    }
    
    
}
