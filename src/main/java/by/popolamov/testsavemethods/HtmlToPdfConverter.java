package by.popolamov.testsavemethods;

/**
 * @author Denis Popolamov
 */

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlToPdfConverter {
    public static void main(String[] args) throws IOException {
        // IO
        File htmlSource = new File("src/main/resources/pdf.html");
        File pdfDest = new File("src/main/resources/file2.pdf");
        // pdfHTML specific code
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(htmlSource),
                new FileOutputStream(pdfDest), converterProperties);
    }
}
