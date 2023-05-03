package by.popolamov.cursework.listeners;

import by.popolamov.cursework.gui.dialogs.PayrollDetailsDialog;
import by.popolamov.cursework.model.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс Listener для формирования больничной выписки в формате PDF
 *
 * @author Denis Popolamov
 */
public class PDFSaveListener implements ActionListener {
    private final PayrollDetailsDialog dialog;
    AverageSalary averageSalary;
    PayrollDetails payrollDetails;
    PayrollMonths payrollMonths;
    Salary salary;
    SickMonthDays sickMonthDays;

    public PDFSaveListener(PayrollDetailsDialog dialog, AverageSalary averageSalary,
                           PayrollDetails payrollDetails, PayrollMonths payrollMonths,
                           Salary salary, SickMonthDays sickMonthDays) {
        this.dialog = dialog;
        this.averageSalary = averageSalary;
        this.payrollDetails = payrollDetails;
        this.payrollMonths = payrollMonths;
        this.salary = salary;
        this.sickMonthDays = sickMonthDays;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить в PDF");

        int userSelection = fileChooser.showSaveDialog(dialog);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                PdfWriter writer = new PdfWriter(new FileOutputStream(fileToSave + ".pdf"));
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);
                PdfFont timesNewRomanFontPdf = PdfFontFactory.createFont("src/main/resources/fonts/TimesNewRoman.ttf");
                // Создаем заголовок документа
                Paragraph para = new Paragraph("ЛИСТОК НЕТРУДОСПОСОБНОСТИ")
                        .setFont(timesNewRomanFontPdf)
                        .setFontSize(20)
                        .setBold().setMarginRight(40)
                        .setTextAlignment(TextAlignment.RIGHT);
                // Создаем линию
                LineSeparator line = new LineSeparator(new SolidLine()).setMarginTop(10).setMarginLeft(100);
                // Создаем div, содержащий заголовок и линию
                Div header = new Div()
                        .add(para)
                        .add(line).setMarginBottom(10);
                document.add(header);

                Cell cSurname = new Cell().add(new Paragraph("Фамилия: " + payrollDetails.getUserSurName()).setFont(timesNewRomanFontPdf));
                Cell cName = new Cell().add(new Paragraph("Имя: " + payrollDetails.getUserName()).setFont(timesNewRomanFontPdf));
                Cell cPatronimic = new Cell().add(new Paragraph("Отчество: " + payrollDetails.getUserPatronymic()).setFont(timesNewRomanFontPdf));

                Div dFio = new Div()
                        .add(cSurname)
                        .add(cName)
                        .add(cPatronimic)
                        .setMarginBottom(10).setMarginLeft(1);
                document.add(dFio);

                Paragraph txtWorkLiberation = new Paragraph("ОСВОБОЖДЕНИЕ ОТ РАБОТЫ (СЛУЖБЫ, УЧЕБЫ)")
                        .setFont(timesNewRomanFontPdf)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER);
                document.add(txtWorkLiberation);

                // Таблица ОСВОБОЖДЕНИЕ ОТ РАБОТЫ
                Table tblWorkLiberation = new Table(4).setMarginBottom(10);
                tblWorkLiberation.setWidth(UnitValue.createPercentValue(100));

                Cell cWorkLiberation = new Cell().add(new Paragraph("C  какого числа \n" +
                        "(число, месяц, год)")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("""
                        По какое число\s
                        включительно\s
                        (число, месяц, год)
                        """)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("""
                        Фамилия, подпись, личная\s
                        печать лечащего врача\s
                        (помощника врача, фельдшера)
                        """)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("Должность, фамилия, подпись, \n" +
                        "личная печать руководителя")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph(payrollDetails.getStartIllnessDate().toString())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph(payrollDetails.getEndIllnessDate().toString())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("Попов")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.LEFT));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("зав. отделением Федоров")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.LEFT));
                tblWorkLiberation.addCell(cWorkLiberation);

                document.add(tblWorkLiberation);

                document.add(new Paragraph("СПРАВКА О ЗАРАБОТНОЙ ПЛАТЕ")
                        .setFont(timesNewRomanFontPdf)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER));

                Table tblSalaryCertificate = new Table(4).setMarginBottom(10);
                tblSalaryCertificate.setWidth(UnitValue.createPercentValue(100));

                Cell cSalaryCertificate = new Cell().add(new Paragraph("Месяцы, взятые для \n" +
                        "начисления пособия")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                cSalaryCertificate = new Cell().add(new Paragraph("""
                        Количество\s
                        рабочих дней\s
                        """)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                cSalaryCertificate = new Cell().add(new Paragraph("""
                        Сумма фактического\s
                        заработка
                        (руб., коп.)""")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                cSalaryCertificate = new Cell().add(new Paragraph("""
                        Среднедневной фактический заработок\s
                        (руб., коп.)
                        """)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                for (int i = 0; i < averageSalary.getAverageSalary().size(); i++) {
                    cSalaryCertificate = new Cell().add(new Paragraph(payrollMonths.getMonth().get(i))
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                    cSalaryCertificate = new Cell().add(new Paragraph(sickMonthDays.getRemainingCalendarDays().get(i).toString())
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                    cSalaryCertificate = new Cell().add(new Paragraph(salary.getSalary().get(i).toString())
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                    cSalaryCertificate = new Cell().add(new Paragraph(averageSalary.getAverageSalary().get(i).toString())
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                }
                document.add(tblSalaryCertificate);

                document.add(new Paragraph("НАЧИСЛЕНО ПОСОБИЕ")
                        .setFont(timesNewRomanFontPdf)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER));

                Table tblPayroll = new Table(new float[]{1, 2, 2, 2, 1, 1, 1}).setMarginBottom(10);
                tblPayroll.setWidth(UnitValue.createPercentValue(100));

                Cell cPayroll = new Cell(2, 1).add(new Paragraph("""
                        Месяцы, количество\s
                        дней\s
                        нетрудоспособности""")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(1, 3).add(new Paragraph("Сумма начисленного пособия")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(2, 1).add(new Paragraph("""
                        Рассчитанная\s
                        максимальная\s
                        сумма пособия\s
                        (руб., коп.)
                        """)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(2, 1).add(new Paragraph("""
                        Рассчитанная\s
                        минимальная\s
                        сумма пособия
                        (руб., коп.)""")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(2, 1).add(new Paragraph("""
                        Сумма\s
                        пособия к\s
                        выплате
                        (руб., коп.)
                        """)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(1, 1).add(new Paragraph("""
                        в размере 80 %\s
                        среднедневного\s
                        заработка""")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(1, 1).add(new Paragraph("""
                        в размере\s
                        100 %\s
                        среднедневного\s
                        заработка""")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph("всего")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(payrollDetails.getCurrentMonth() + ", " + payrollDetails.getIllnessDays())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(payrollDetails.getEightyPercentSalary()))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(payrollDetails.getHundredPercentSalary()))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(payrollDetails.getTotalPayrollSum()))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(payrollDetails.getTotalPayrollSum()))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(payrollDetails.getTotalPayrollSum()))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(payrollDetails.getTotalPayrollSum()))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                document.add(tblPayroll);

                document.close();

                JOptionPane.showMessageDialog(dialog,
                        "Файл успешно сохранен в формате PDF!", "Файл сохранен",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Ошибка при сохранении файла!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
