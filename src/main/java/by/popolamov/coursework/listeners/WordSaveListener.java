package by.popolamov.coursework.listeners;

import by.popolamov.coursework.gui.dialogs.PayrollDetailsDialog;
import by.popolamov.coursework.model.*;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс Listener для формирования больничной выписки в формате Word
 *
 * @author Denis Popolamov
 */
public class WordSaveListener implements ActionListener {
    private final PayrollDetailsDialog dialog;
    AverageSalary averageSalary;
    PayrollDetails payrollDetails;
    PayrollMonths payrollMonths;
    Salary salary;
    SickMonthDays sickMonthDays;

    public WordSaveListener(PayrollDetailsDialog dialog, AverageSalary averageSalary,
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
        fileChooser.setDialogTitle("Сохранить в Word");
        int userSelection = fileChooser.showSaveDialog(dialog);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph headParagraph = document.createParagraph();
            headParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun headRun = headParagraph.createRun();
            headRun.setFontSize(20);
            headRun.setFontFamily("Times New Roman");
            headRun.setBold(true);
            headRun.setText("ЛИСТОК НЕТРУДОСПОСОБНОСТИ");
            headRun.addBreak();

            XWPFParagraph fcsParagraph = document.createParagraph();
            XWPFRun fcsRun = fcsParagraph.createRun();
            fcsRun.setFontSize(14);
            fcsRun.setFontFamily("Times New Roman");

            fcsRun.setText("Фамилия: " + payrollDetails.getUserSurName());
            fcsRun.addBreak();
            fcsRun.setText("Имя: " + payrollDetails.getUserName());
            fcsRun.addBreak();
            fcsRun.setText("Отчество: " + payrollDetails.getUserPatronymic());
            fcsRun.addBreak();

            XWPFParagraph paragraphWorkLiberation = document.createParagraph();
            paragraphWorkLiberation.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun runWorkLiberation = paragraphWorkLiberation.createRun();
            runWorkLiberation.setFontFamily("Times New Roman");
            runWorkLiberation.setBold(true);

            runWorkLiberation.setText("ОСВОБОЖДЕНИЕ ОТ РАБОТЫ (СЛУЖБЫ, УЧЕБЫ):");
            runWorkLiberation.setFontSize(12);

            XWPFTable tblWorkLiberation = document.createTable(2, 4);
            tblWorkLiberation.setWidth("100%");
            tblWorkLiberation.getRow(0).getCell(0).setText("""
                    C какого числа
                    (число, месяц,
                    год)""");
            tblWorkLiberation.getRow(0).getCell(1).setText("""
                    По какое число
                    включительно
                    (число, месяц,
                    год)
                    """);
            tblWorkLiberation.getRow(0).getCell(2).setText("""
                    Фамилия, подпись, личная
                    печать лечащего врача
                    (помощника врача,
                    фельдшера)""");
            tblWorkLiberation.getRow(0).getCell(3).setText("""
                    Должность, фамилия,
                    подпись,
                    личная печать руководителя""");
            tblWorkLiberation.getRow(1).getCell(0).setText(payrollDetails.getStartIllnessDate().toString());
            tblWorkLiberation.getRow(1).getCell(1).setText(payrollDetails.getEndIllnessDate().toString());
            tblWorkLiberation.getRow(1).getCell(2).setText("Попов");
            tblWorkLiberation.getRow(1).getCell(3).setText("зав. отделением Федоров");

            XWPFParagraph paragraphSalaryCertificate = document.createParagraph();
            paragraphSalaryCertificate.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun runSalaryCertificate = paragraphSalaryCertificate.createRun();
            runSalaryCertificate.setFontFamily("Times New Roman");
            runSalaryCertificate.setBold(true);

            runSalaryCertificate.setText("СПРАВКА О ЗАРАБОТНОЙ ПЛАТЕ");
            runSalaryCertificate.setFontSize(12);

            XWPFTable tblSalaryCertificate = document.createTable(7, 4);
            tblSalaryCertificate.setWidth("100%");
            tblSalaryCertificate.getRow(0).getCell(0).setText("Месяцы, взятые для\n" +
                    "начисления пособия");
            tblSalaryCertificate.getRow(0).getCell(1).setText("Количество\n" +
                    "рабочих дней");
            tblSalaryCertificate.getRow(0).getCell(2).setText("""
                    Сумма фактического
                    заработка
                    (руб., коп.)
                    """);
            tblSalaryCertificate.getRow(0).getCell(3).setText("""
                    Среднедневной фактический заработок
                    (руб., коп.)
                    """);
            for (int i = 0; i < averageSalary.getAverageSalary().size(); i++) {
                tblSalaryCertificate.getRow(i + 1).getCell(0)
                        .setText(payrollMonths.getMonth().get(i));
                tblSalaryCertificate.getRow(i + 1).getCell(1)
                        .setText(sickMonthDays.getRemainingCalendarDays().get(i).toString());
                tblSalaryCertificate.getRow(i + 1).getCell(2)
                        .setText(salary.getSalary().get(i).toString());
                tblSalaryCertificate.getRow(i + 1).getCell(3)
                        .setText(averageSalary.getAverageSalary().get(i).toString());
            }

            XWPFParagraph paragraphPayroll = document.createParagraph();
            paragraphPayroll.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun runPayroll = paragraphPayroll.createRun();
            runPayroll.setFontFamily("Times New Roman");
            runPayroll.setBold(true);

            runPayroll.setText("СПРАВКА О ЗАРАБОТНОЙ ПЛАТЕ");
            runPayroll.setFontSize(12);

            XWPFTable tblPayroll = document.createTable(3, 7);
            tblPayroll.setWidth("100%");

            tblPayroll.getRow(0).getCell(0).setText("""
                    Месяцы, количество
                    дней
                    нетрудоспособности""");
            tblPayroll.getRow(0).getCell(1).setText("Сумма начисленного пособия");
            tblPayroll.getRow(0).getCell(2);
            tblPayroll.getRow(0).getCell(3);
            tblPayroll.getRow(0).getCell(4).setText("""
                    Рассчитанная
                    максимальная
                    сумма пособия
                    (руб., коп.)
                    """);
            tblPayroll.getRow(0).getCell(5).setText("""
                    Рассчитанная
                    минимальная
                    сумма пособия
                    (руб., коп.)""");
            tblPayroll.getRow(0).getCell(6).setText("""
                    Сумма
                    пособия к
                    выплате
                    (руб., коп.)""");
            tblPayroll.getRow(1).getCell(0);
            tblPayroll.getRow(1).getCell(1).setText("""
                    в размере 80 %
                    среднедневного
                    заработка""");
            tblPayroll.getRow(1).getCell(2).setText("""
                    в размере
                    100 %
                    среднедневного
                    заработка""");
            tblPayroll.getRow(1).getCell(3).setText("всего");
            tblPayroll.getRow(1).getCell(4);
            tblPayroll.getRow(1).getCell(5);
            tblPayroll.getRow(1).getCell(6);
            tblPayroll.getRow(2).getCell(0)
                    .setText(payrollDetails.getCurrentMonth() + ", " + payrollDetails.getIllnessDays());
            tblPayroll.getRow(2).getCell(1).setText(String.valueOf(payrollDetails.getEightyPercentSalary()));
            tblPayroll.getRow(2).getCell(2).setText(String.valueOf(payrollDetails.getHundredPercentSalary()));
            tblPayroll.getRow(2).getCell(3).setText(String.valueOf(payrollDetails.getTotalPayrollSum()));
            tblPayroll.getRow(2).getCell(4).setText(String.valueOf(payrollDetails.getTotalPayrollSum()));
            tblPayroll.getRow(2).getCell(5).setText(String.valueOf(payrollDetails.getTotalPayrollSum()));
            tblPayroll.getRow(2).getCell(6).setText(String.valueOf(payrollDetails.getTotalPayrollSum()));

            tblPayroll.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            tblPayroll.getRow(0).getCell(4).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            tblPayroll.getRow(0).getCell(5).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            tblPayroll.getRow(0).getCell(6).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);

            tblPayroll.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            tblPayroll.getRow(1).getCell(4).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            tblPayroll.getRow(1).getCell(5).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            tblPayroll.getRow(1).getCell(6).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);

            tblPayroll.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            tblPayroll.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            tblPayroll.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

            try {
                FileOutputStream out = new FileOutputStream(fileToSave + ".docx");
                document.write(out);
                out.close();
                document.close();
                JOptionPane.showMessageDialog(dialog,
                        "Файл успешно сохранен в формате Word!", "Файл сохранен",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Ошибка при сохранении файла!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}