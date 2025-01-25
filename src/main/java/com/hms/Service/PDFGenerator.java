package com.hms.Service;

import com.hms.Entity.Booking;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

@Service
public class PDFGenerator {
    public String generatePDF(String fileName, Booking sb, String type, Booking saveBook){
        String pdfPath = "src/main/resources/" + fileName;

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add a paragraph to the document
            document.add(new Paragraph("Hello, this is a sample PDF generated using iText 7.\n"));
            document.add(new Paragraph("This is the Guest Details"));

            Table table = new Table(4);
            table.addCell("Guest_name");
            table.addCell("No of Guest");
            table.addCell("Mobile No");
            table.addCell("Room Type");
            table.addCell(sb.getGuest_Name());
            table.addCell(String.valueOf(sb.getNoOfGuest()));
            table.addCell(String.valueOf(sb.getPhone()));
            table.addCell(type);

            document.add(table);

            document.add(new Paragraph("\nThis is the Room Availability"));

            Table rm = new Table(2);
            rm.addCell("Property");
            rm.addCell(String.valueOf(saveBook.getProperty()));

            document.add(rm);
            // Close the document
            document.close();

            System.out.println("PDF created successfully at: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfPath;
    }
}
