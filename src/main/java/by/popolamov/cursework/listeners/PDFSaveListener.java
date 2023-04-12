package by.popolamov.cursework.listeners;

import by.popolamov.cursework.gui.dialogs.PayrollDetailsDialog;
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
import java.util.List;

/**
 * Класс Listener для формирования больничной выписки в формате PDF
 *
 * @author Denis Popolamov
 */
public class PDFSaveListener implements ActionListener {
    private PayrollDetailsDialog dialog;
    private JLabel lblUserSurname;
    private JLabel lblUserName;
    private JLabel lblUserPatronimic;
    private JLabel lblStartIllnessDate;
    private JLabel lblEndIllnessDate;
    private JLabel lblTotalSickDates;
    private JLabel lblTotalPayrollSum;
    List<Double> averageSalaryList;
    List<String> monthList;

    List<Double> salaryList;
    List<Integer> remainingCalendarDaysList;
    String currentMonth;
    Double eightyPecentSalary;
    Double hundredPercentSalary;
    int numberOfIllnessDays;

    public PDFSaveListener(PayrollDetailsDialog dialog, JLabel lblUserSurname,
                           JLabel lblUserName, JLabel lblUserPatronimic,
                           JLabel lblStartIllnessDate, JLabel lblEndIllnessDate,
                           JLabel lblTotalSickDates, JLabel lblTotalPayrollSum,
                           List<Double> averageSalaryList, List<String> monthList,
                           List<Double> salaryList, List<Integer> remainingCalendarDaysList,
                           String currentMonth, Double eightyPecentSalary,
                           Double hundredPercentSalary, int numberOfIllnessDays) {
        this.dialog = dialog;
        this.lblUserSurname = lblUserSurname;
        this.lblUserName = lblUserName;
        this.lblUserPatronimic = lblUserPatronimic;
        this.lblStartIllnessDate = lblStartIllnessDate;
        this.lblEndIllnessDate = lblEndIllnessDate;
        this.lblTotalSickDates = lblTotalSickDates;
        this.lblTotalPayrollSum = lblTotalPayrollSum;
        this.averageSalaryList = averageSalaryList;
        this.monthList = monthList;
        this.salaryList = salaryList;
        this.remainingCalendarDaysList = remainingCalendarDaysList;
        this.currentMonth = currentMonth;
        this.eightyPecentSalary = eightyPecentSalary;
        this.hundredPercentSalary = hundredPercentSalary;
        this.numberOfIllnessDays = numberOfIllnessDays;
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
                PdfFont arialFontPdf = PdfFontFactory.createFont("src/main/resources/fonts/Arial.ttf");
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

                Cell cSurname = new Cell().add(new Paragraph("Фамилия: " + lblUserSurname.getText()).setFont(timesNewRomanFontPdf));
                Cell cName = new Cell().add(new Paragraph("Имя: " + lblUserName.getText()).setFont(timesNewRomanFontPdf));
                Cell cPatronimic = new Cell().add(new Paragraph("Отчество: " + lblUserPatronimic.getText()).setFont(timesNewRomanFontPdf));

                Div dFio = new Div()
                        .add(cSurname)
                        .add(cName)
                        .add(cPatronimic)
                        .setMarginBottom(10).setMarginLeft(1);
                document.add(dFio);

//                Paragraph vkkText = new Paragraph("ВКК:")
//                        .setFont(timesNewRomanFontPdf)
//                        .setFontSize(12)
//                        .setTextAlignment(TextAlignment.CENTER);
//                document.add(vkkText);
//
//                // Таблица ВКК
//                Table vkkTable = new Table(new float[]{1, 2, 2, 1}).setMarginBottom(10);
//                vkkTable.setWidth(UnitValue.createPercentValue(100));
//
//                Cell vkkCell = new Cell(2, 1).add(new Paragraph("Дата")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//
//                vkkCell = new Cell(1, 2).add(new Paragraph("Длительность ВН")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//
//                vkkCell = new Cell(2, 1).add(new Paragraph("Должность, фамилия, подпись, \n" +
//                        "личная печать председателя ВКК")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//
//                vkkCell = new Cell().add(new Paragraph("непрерывная")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//
//                vkkCell = new Cell().add(new Paragraph("суммарная")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//                vkkCell = new Cell().add(new Paragraph("текст1")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//                vkkCell = new Cell().add(new Paragraph("текст2")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//                vkkCell = new Cell().add(new Paragraph("текст3")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//                vkkCell = new Cell().add(new Paragraph("текст4")
//                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                vkkTable.addCell(vkkCell);
//
//                document.add(vkkTable);

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
                cWorkLiberation = new Cell().add(new Paragraph("По какое число \n" +
                        "включительно \n" +
                        "(число, месяц, год)\n")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("Фамилия, подпись, личная \n" +
                        "печать лечащего врача \n" +
                        "(помощника врача, фельдшера)\n")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph("Должность, фамилия, подпись, \n" +
                        "личная печать руководителя")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph(lblStartIllnessDate.getText())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblWorkLiberation.addCell(cWorkLiberation);
                cWorkLiberation = new Cell().add(new Paragraph(lblEndIllnessDate.getText())
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
                cSalaryCertificate = new Cell().add(new Paragraph("Количество \n" +
                        "рабочих дней \n")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                cSalaryCertificate = new Cell().add(new Paragraph("Сумма фактического \n" +
                        "заработка\n" +
                        "(руб., коп.)")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                cSalaryCertificate = new Cell().add(new Paragraph("Среднедневной фактический заработок \n" +
                        "(руб., коп.)\n")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                tblSalaryCertificate.addCell(cSalaryCertificate);
                for (int i = 0; i < averageSalaryList.size(); i++) {
                    cSalaryCertificate = new Cell().add(new Paragraph(monthList.get(i).toString())
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                    cSalaryCertificate = new Cell().add(new Paragraph(remainingCalendarDaysList.get(i).toString())
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                    cSalaryCertificate = new Cell().add(new Paragraph(salaryList.get(i).toString())
                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
                    tblSalaryCertificate.addCell(cSalaryCertificate);
                    cSalaryCertificate = new Cell().add(new Paragraph(averageSalaryList.get(i).toString())
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

                Cell cPayroll = new Cell(2, 1).add(new Paragraph("Месяцы, количество \n" +
                        "дней \n" +
                        "нетрудоспособности")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(1, 3).add(new Paragraph("Сумма начисленного пособия")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(2, 1).add(new Paragraph("Рассчитанная \n" +
                        "максимальная \n" +
                        "сумма пособия \n" +
                        "(руб., коп.)\n")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(2, 1).add(new Paragraph("Рассчитанная \n" +
                        "минимальная \n" +
                        "сумма пособия\n" +
                        "(руб., коп.)")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(2, 1).add(new Paragraph("Сумма \n" +
                        "пособия к \n" +
                        "выплате\n" +
                        "(руб., коп.)\n")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(1, 1).add(new Paragraph("в размере 80 % \n" +
                        "среднедневного \n" +
                        "заработка")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell(1, 1).add(new Paragraph("в размере \n" +
                        "100 % \n" +
                        "среднедневного \n" +
                        "заработка")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph("всего")
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(currentMonth + ", " + numberOfIllnessDays)
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(eightyPecentSalary))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(String.valueOf(hundredPercentSalary))
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
                        .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
                tblPayroll.addCell(cPayroll);
                cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
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
