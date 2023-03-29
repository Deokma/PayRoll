package by.popolamov.cursework.gui;


import by.popolamov.cursework.connect.DBManager;
import by.popolamov.cursework.listeners.PDFSaveListener;
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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PayrollDetailsDialog extends JDialog {

    private final Object[] payrollDetails;
    private JLabel lblUserSurname;
    private JLabel lblUserName;
    private JLabel lblUserPatronimic;
    private JLabel lblStartIllnessDate;
    private JLabel lblEndIllnessDate;
    private JLabel lblTotalSickDates;
    private JLabel lblTotalSalary;
    private JLabel lblTotalRemainingDays;
    private JLabel lblTotalAverageSalary;
    private JLabel lblTotalPayrollSum;
    List<Double> averageSalaryList;
    List<String> monthList;
    List<Double> salaryList;
    List<Integer> sickMonthDaysList;
    List<Integer> remainingCalendarDaysList;
    String currentMonth;
    Double eightyPecentSalary;
    Double hundredPercentSalary;
    // Создаем экземпляр PDFButtonListener


// Добавляем слушателя к кнопке

    DBManager db = new DBManager();

    public PayrollDetailsDialog(Frame parent, Object[] payrollDetails) {
        super(parent, "Деталь выплаты", true);
        this.payrollDetails = payrollDetails;
        initComponents();

        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Создаем JLabel'ы для отображения данных
        lblUserSurname = new JLabel();
        lblUserName = new JLabel();
        lblUserPatronimic = new JLabel();
        lblStartIllnessDate = new JLabel();
        lblEndIllnessDate = new JLabel();
        lblTotalSickDates = new JLabel();
        lblTotalSalary = new JLabel();
        lblTotalRemainingDays = new JLabel();
        lblTotalAverageSalary = new JLabel();
        lblTotalPayrollSum = new JLabel();
        averageSalaryList = new ArrayList<>();
        monthList = new ArrayList<>();
        salaryList = new ArrayList<>();
        sickMonthDaysList = new ArrayList<>();
        remainingCalendarDaysList = new ArrayList<>();
        currentMonth = null;
        eightyPecentSalary = null;
        hundredPercentSalary = null;
        //System.out.println(averageSalaryList.toString());

        // Создаем панель для размещения JLabel'ов
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel pnlTop = new JPanel(new BorderLayout());
        JLabel tittleLabel = new JLabel("Выписка об нетрудоспособности");
        tittleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tittleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pnlTop.add(tittleLabel, BorderLayout.CENTER);
        pnl.add(pnlTop, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        centerPanel.add(new JLabel("Фамилия:"));
        centerPanel.add(lblUserSurname);
        centerPanel.add(new JLabel("Имя:"));
        centerPanel.add(lblUserName);
        centerPanel.add(new JLabel("Отчество:"));
        centerPanel.add(lblUserPatronimic);
        centerPanel.add(new JLabel("Нач. дата болезни:"));
        centerPanel.add(lblStartIllnessDate);
        centerPanel.add(new JLabel("Кон. дата болезни:"));
        centerPanel.add(lblEndIllnessDate);
        centerPanel.add(new JLabel("Кол. дней нетруд. за прошлые 6 мес.:"));
        centerPanel.add(lblTotalSickDates);
        centerPanel.add(new JLabel("Общая зарплата за 6 месяцев:"));
        centerPanel.add(lblTotalSalary);
        centerPanel.add(new JLabel("Всего рабочих дней за 6 месяцев:"));
        centerPanel.add(lblTotalRemainingDays);
        centerPanel.add(new JLabel("Общая средняя заработная плата:"));
        centerPanel.add(lblTotalAverageSalary);
        centerPanel.add(new JLabel("Общая сумма выплаты:"));
        centerPanel.add(lblTotalPayrollSum);
        pnl.add(centerPanel, BorderLayout.CENTER);

        // Размещаем панель на форме
        getContentPane().add(pnl, BorderLayout.CENTER);

        // Создаем кнопки
        JButton btnWord = new JButton("Word");
        JButton btnExcel = new JButton("Excel");
        JButton btnPDF = new JButton("PDF");

        // Устанавливаем цвета кнопок
        btnWord.setBackground(new Color(43, 87, 154));
        btnWord.setForeground(Color.WHITE);
        btnExcel.setBackground(new Color(33, 115, 70));
        btnExcel.setForeground(Color.WHITE);
        btnPDF.setBackground(new Color(179, 11, 0));
        btnPDF.setForeground(Color.WHITE);

        JButton btnCancel = new JButton("Отмена");
        btnCancel.setBackground(new Color(226, 27, 34));
        btnCancel.setForeground(new Color(255, 255, 255));

        System.out.println(averageSalaryList.size());

        // Настраиваем размеры окна и его положение на экране
        JPanel pnlButtom = new JPanel(new BorderLayout());
        pnlButtom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Создаем панель для кнопок справа
        JPanel pnlRightButton = new JPanel(new GridLayout(1, 1, 20, 10));
        JPanel pnlLeftButton = new JPanel(new GridLayout(2, 1, 20, 10));
        JPanel pnlTopLeftButton = new JPanel(new BorderLayout());
        JLabel lblSaveAs = new JLabel("Сохранить как:");
        lblSaveAs.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaveAs.setFont(new Font("Arial", Font.BOLD, 16));
        pnlTopLeftButton.add(lblSaveAs);
        JPanel pnlButtomLeftButton = new JPanel(new GridLayout(1, 3, 20, 10));

        pnlButtomLeftButton.add(btnWord);
        pnlButtomLeftButton.add(btnExcel);
        pnlButtomLeftButton.add(btnPDF);

        pnlLeftButton.add(pnlTopLeftButton);
        pnlLeftButton.add(pnlButtomLeftButton);

        pnlRightButton.add(btnCancel);
        pnlButtom.add(pnlLeftButton, BorderLayout.WEST);
        pnlButtom.add(pnlRightButton, BorderLayout.EAST);
        add(pnlButtom, BorderLayout.SOUTH);
        setSize(600, 350);
        setLocationRelativeTo(null);
        // Слушатель для закрытия окна
        btnCancel.addActionListener(e -> {
            dispose();
        });

//        btnPDF.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Сохранить в PDF");
//
//            int userSelection = fileChooser.showSaveDialog(PayrollDetailsDialog.this);
//
//            if (userSelection == JFileChooser.APPROVE_OPTION) {
//                File fileToSave = fileChooser.getSelectedFile();
//                try {
//                    PdfWriter writer = new PdfWriter(new FileOutputStream(fileToSave + ".pdf"));
//                    PdfDocument pdfDoc = new PdfDocument(writer);
//                    Document document = new Document(pdfDoc);
//                    PdfFont timesNewRomanFontPdf = PdfFontFactory.createFont("src/main/resources/fonts/TimesNewRoman.ttf");
//                    PdfFont arialFontPdf = PdfFontFactory.createFont("src/main/resources/fonts/Arial.ttf");
//
//                    // Создаем заголовок документа
//                    Paragraph para = new Paragraph("ЛИСТОК НЕТРУДОСПОСОБНОСТИ")
//                            .setFont(timesNewRomanFontPdf)
//                            .setFontSize(20)
//                            .setBold().setMarginRight(40)
//                            .setTextAlignment(TextAlignment.RIGHT);
//
//                    // Создаем линию
//                    LineSeparator line = new LineSeparator(new SolidLine()).setMarginTop(10).setMarginLeft(100);
//
//                    // Создаем div, содержащий заголовок и линию
//                    Div header = new Div()
//                            .add(para)
//                            .add(line).setMarginBottom(10);
//                    // Добавляем div в документ
//                    document.add(header);
//                    // Добавляем отступ перед таблицей
//                    //document.add(new Paragraph("").setMarginTop(10));
//
//                    Cell cSurname = new Cell().add(new Paragraph("Фамилия: " + lblUserSurname.getText()).setFont(timesNewRomanFontPdf));
//                    Cell cName = new Cell().add(new Paragraph("Имя: " + lblUserName.getText()).setFont(timesNewRomanFontPdf));
//                    Cell cPatronimic = new Cell().add(new Paragraph("Отчество: " + lblUserPatronimic.getText()).setFont(timesNewRomanFontPdf));
//
//                    Div dFio = new Div()
//                            .add(cSurname)
//                            .add(cName)
//                            .add(cPatronimic)
//                            .setMarginBottom(10).setMarginLeft(1);
//                    document.add(dFio);
//
//                    Paragraph vkkText = new Paragraph("ВКК:")
//                            .setFont(timesNewRomanFontPdf)
//                            .setFontSize(12)
//                            .setTextAlignment(TextAlignment.CENTER);
//                    document.add(vkkText);
//
//                    // Таблица ВКК
//                    Table vkkTable = new Table(new float[]{1, 2, 2, 1}).setMarginBottom(10);
//                    vkkTable.setWidth(UnitValue.createPercentValue(100));
//
//                    Cell vkkCell = new Cell(2, 1).add(new Paragraph("Дата")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//
//                    vkkCell = new Cell(1, 2).add(new Paragraph("Длительность ВН")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//
//                    vkkCell = new Cell(2, 1).add(new Paragraph("Должность, фамилия, подпись, \n" +
//                            "личная печать председателя ВКК")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//
//                    vkkCell = new Cell().add(new Paragraph("непрерывная")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//
//                    vkkCell = new Cell().add(new Paragraph("суммарная")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//                    vkkCell = new Cell().add(new Paragraph("текст1")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//                    vkkCell = new Cell().add(new Paragraph("текст2")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//                    vkkCell = new Cell().add(new Paragraph("текст3")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//                    vkkCell = new Cell().add(new Paragraph("текст4")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    vkkTable.addCell(vkkCell);
//
//
//                    document.add(vkkTable);
//
//                    // Таблица
////                    Table testTable = new Table(2).setMarginBottom(10);
////                    testTable.setWidth(UnitValue.createPercentValue(100));
////
////                    Cell cell = new Cell().add(new Paragraph("Нач. дата болезни:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblStartIllnessDate.getText()));
////                    testTable.addCell(cell);
////
////                    cell = new Cell().add(new Paragraph("Кон. дата болезни:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblEndIllnessDate.getText()));
////                    testTable.addCell(cell);
////
////                    cell = new Cell().add(new Paragraph("Кол. дней нетруд. за прошлые 6 мес:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblTotalSickDates.getText()));
////                    testTable.addCell(cell);
////
////                    cell = new Cell().add(new Paragraph("Общая зарплата за 6 месяцев:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblTotalSalary.getText()));
////                    testTable.addCell(cell);
////
////                    cell = new Cell().add(new Paragraph("Всего рабочих дней за 6 месяцев:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblTotalRemainingDays.getText()));
////                    testTable.addCell(cell);
////
////                    cell = new Cell().add(new Paragraph("Общая средняя заработная плата:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblTotalAverageSalary.getText()));
////                    testTable.addCell(cell);
////
////                    cell = new Cell().add(new Paragraph("Общая сумма выплаты:").setFont(arialFontPdf));
////                    testTable.addCell(cell);
////                    cell = new Cell().add(new Paragraph(lblTotalPayrollSum.getText()));
////                    testTable.addCell(cell);
////
////                    document.add(testTable);
//
//                    Paragraph txtWorkLiberation = new Paragraph("ОСВОБОЖДЕНИЕ ОТ РАБОТЫ (СЛУЖБЫ, УЧЕБЫ):")
//                            .setFont(timesNewRomanFontPdf)
//                            .setFontSize(12)
//                            .setTextAlignment(TextAlignment.CENTER);
//                    document.add(txtWorkLiberation);
//
//                    // Таблица ОСВОБОЖДЕНИЕ ОТ РАБОТЫ
//                    Table tblWorkLiberation = new Table(4).setMarginBottom(10);
//                    tblWorkLiberation.setWidth(UnitValue.createPercentValue(100));
//
//                    Cell cWorkLiberation = new Cell().add(new Paragraph("C  какого числа \n" +
//                            "(число, месяц, год)")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//
//                    cWorkLiberation = new Cell().add(new Paragraph("По какое число \n" +
//                            "включительно \n" +
//                            "(число, месяц, год)\n")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//
//                    cWorkLiberation = new Cell().add(new Paragraph("Фамилия, подпись, личная \n" +
//                            "печать лечащего врача \n" +
//                            "(помощника врача, фельдшера)\n")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//
//                    cWorkLiberation = new Cell().add(new Paragraph("Должность, фамилия, подпись, \n" +
//                            "личная печать руководителя")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//
//                    cWorkLiberation = new Cell().add(new Paragraph(lblStartIllnessDate.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//                    cWorkLiberation = new Cell().add(new Paragraph(lblEndIllnessDate.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//                    cWorkLiberation = new Cell().add(new Paragraph("")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//                    cWorkLiberation = new Cell().add(new Paragraph("")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblWorkLiberation.addCell(cWorkLiberation);
//
//                    document.add(tblWorkLiberation);
//
//
//                    document.add(new Paragraph("СПРАВКА О ЗАРАБОТНОЙ ПЛАТЕ")
//                            .setFont(timesNewRomanFontPdf)
//                            .setFontSize(12)
//                            .setTextAlignment(TextAlignment.CENTER));
//
//                    Table tblSalaryCertificate = new Table(4).setMarginBottom(10);
//                    tblSalaryCertificate.setWidth(UnitValue.createPercentValue(100));
//
//                    Cell cSalaryCertificate = new Cell().add(new Paragraph("Месяцы, взятые для \n" +
//                            "начисления пособия")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblSalaryCertificate.addCell(cSalaryCertificate);
//
//                    cSalaryCertificate = new Cell().add(new Paragraph("Количество \n" +
//                            "рабочих дней \n")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblSalaryCertificate.addCell(cSalaryCertificate);
//
//                    cSalaryCertificate = new Cell().add(new Paragraph("Сумма фактического \n" +
//                            "заработка\n" +
//                            "(руб., коп.)")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblSalaryCertificate.addCell(cSalaryCertificate);
//
//                    cSalaryCertificate = new Cell().add(new Paragraph("Среднедневной фактический заработок \n" +
//                            "(руб., коп.)\n")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                    tblSalaryCertificate.addCell(cSalaryCertificate);
//
//                    for (int i = 0; i < averageSalaryList.size(); i++) {
//                        cSalaryCertificate = new Cell().add(new Paragraph(monthList.get(i).toString())
//                                .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                        tblSalaryCertificate.addCell(cSalaryCertificate);
//                        cSalaryCertificate = new Cell().add(new Paragraph(remainingCalendarDaysList.get(i).toString())
//                                .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                        tblSalaryCertificate.addCell(cSalaryCertificate);
//                        cSalaryCertificate = new Cell().add(new Paragraph(salaryList.get(i).toString())
//                                .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                        tblSalaryCertificate.addCell(cSalaryCertificate);
//                        cSalaryCertificate = new Cell().add(new Paragraph(averageSalaryList.get(i).toString())
//                                .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER));
//                        tblSalaryCertificate.addCell(cSalaryCertificate);
//                    }
//                    document.add(tblSalaryCertificate);
//
//                    document.add(new Paragraph("НАЧИСЛЕНО ПОСОБИЕ")
//                            .setFont(timesNewRomanFontPdf)
//                            .setFontSize(12)
//                            .setTextAlignment(TextAlignment.CENTER));
//
//                    Table tblPayroll = new Table(new float[]{1, 2, 2, 2, 1, 1, 1}).setMarginBottom(10);
//                    tblPayroll.setWidth(UnitValue.createPercentValue(100));
//
//                    Cell cPayroll = new Cell(2, 1).add(new Paragraph("Месяцы, количество \n" +
//                            "дней \n" +
//                            "нетрудоспособности")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell(1, 3).add(new Paragraph("Сумма начисленного пособия")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell(2, 1).add(new Paragraph("Рассчитанная \n" +
//                            "максимальная \n" +
//                            "сумма пособия \n" +
//                            "(руб., коп.)\n")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell(2, 1).add(new Paragraph("Рассчитанная \n" +
//                            "минимальная \n" +
//                            "сумма пособия\n" +
//                            "(руб., коп.)")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell(2, 1).add(new Paragraph("Сумма \n" +
//                            "пособия к \n" +
//                            "выплате\n" +
//                            "(руб., коп.)\n")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell(1, 1).add(new Paragraph("в размере 80 % \n" +
//                            "среднедневного \n" +
//                            "заработка")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell(1, 1).add(new Paragraph("в размере \n" +
//                            "100 % \n" +
//                            "среднедневного \n" +
//                            "заработка")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell().add(new Paragraph("всего")
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell().add(new Paragraph(currentMonth.toString() + ", " + lblTotalSickDates.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell().add(new Paragraph(eightyPecentSalary.toString())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell().add(new Paragraph(hundredPercentSalary.toString())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//                    cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//                    cPayroll = new Cell().add(new Paragraph(lblTotalPayrollSum.getText())
//                            .setFont(timesNewRomanFontPdf).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
//                    tblPayroll.addCell(cPayroll);
//
//
//                    document.add(tblPayroll);
//
//                    document.close();
//
//                    JOptionPane.showMessageDialog(PayrollDetailsDialog.this,
//                            "Файл успешно сохранен в формате PDF!", "Файл сохранен",
//                            JOptionPane.INFORMATION_MESSAGE);
//
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(PayrollDetailsDialog.this,
//                            "Ошибка при сохранении файла!", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
        btnPDF.addActionListener(e -> {
            PDFSaveListener btnPDFListener = new PDFSaveListener(this, lblUserSurname,
                    lblUserName, lblUserPatronimic,
                    lblStartIllnessDate, lblEndIllnessDate,
                    lblTotalSickDates, lblTotalPayrollSum,
                    averageSalaryList, monthList,
                    salaryList, remainingCalendarDaysList,
                    currentMonth, eightyPecentSalary,
                    hundredPercentSalary);
            btnPDFListener.actionPerformed(e);
        });

        //btnPDF.addActionListener(btnPDFListener);
    }

    // Метод для установки значений в JLabel'ы
    public void setPayrollDetails(Object[] payrollDetails) {

        long payroll_id = (long) payrollDetails[0];
        lblUserSurname.setText(payrollDetails[1].toString());
        lblUserName.setText(payrollDetails[2].toString());
        lblUserPatronimic.setText(payrollDetails[3].toString());
        lblStartIllnessDate.setText(payrollDetails[4].toString());
        lblEndIllnessDate.setText(payrollDetails[5].toString());
        lblTotalSickDates.setText(payrollDetails[6].toString());
        lblTotalSalary.setText(payrollDetails[7].toString());
        lblTotalRemainingDays.setText(payrollDetails[8].toString());
        lblTotalAverageSalary.setText(payrollDetails[9].toString());
        lblTotalPayrollSum.setText(payrollDetails[10].toString());
        currentMonth = payrollDetails[11].toString();
        eightyPecentSalary = Double.valueOf(payrollDetails[12].toString());
        hundredPercentSalary = Double.valueOf(payrollDetails[13].toString());

        averageSalaryList = db.getAverageSalaryByPayrollId(payroll_id);
        monthList = db.getPayrollMonthsByPayrollId(payroll_id);
        salaryList = db.getSalaryByPayrollId(payroll_id);
        sickMonthDaysList = db.getSickMonthDaysByPayrollId(payroll_id);
        remainingCalendarDaysList = db.getRemainingDaysByPayrollId(payroll_id);
    }

}

