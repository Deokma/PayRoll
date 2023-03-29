package by.popolamov.testsavemethods;

/**
 * @author Denis Popolamov
 */
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

public class PdfEditor {

    public static void replaceFieldValue(String fieldName, String newValue, PdfAcroForm form) {
        PdfFormField field = form.getField(fieldName);
        if (field != null) {
            field.setValue(newValue);
        }
    }

    public static void editPDF(String inputFile, String outputFile) throws IOException {
        // Load input PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputFile), new PdfWriter(outputFile));

        // Get the form fields of the PDF
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        // Update the "Фамилия" field to "bb"
        replaceFieldValue("Имя", "bb", form);

        // Save the updated PDF document
        pdfDoc.close();
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "src/main/resources/draw.pdf";
        String outputFile = "src/main/resources/draw2.pdf";

        // Edit the input PDF document and save the updated version
        editPDF(inputFile, outputFile);
    }

}
