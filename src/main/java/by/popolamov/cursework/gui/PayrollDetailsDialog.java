package by.popolamov.cursework.gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PayrollDetailsDialog extends JDialog {

    private final Object[] payrollDetails;
    private JLabel labelUserSurname;
    private JLabel labelUserName;
    private JLabel labelUserPatronimic;
    private JLabel startIllnessDateLabel;
    private JLabel endIllnessDateLabel;
    private JLabel labelTotalSickDates;
    private JLabel labelTotalSalary;
    private JLabel labelTotalRemainingDays;
    private JLabel labelTotalAverageSalary;
    private JLabel labelTotalPayrollSum;


    public PayrollDetailsDialog(Frame parent, Object[] payrollDetails) {
        super(parent, "Деталь выплаты", true);
        this.payrollDetails = payrollDetails;
        initComponents();

        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Создаем JLabel'ы для отображения данных
        labelUserSurname = new JLabel();
        labelUserName = new JLabel();
        labelUserPatronimic = new JLabel();
        startIllnessDateLabel = new JLabel();
        endIllnessDateLabel = new JLabel();
        labelTotalSickDates = new JLabel();
        labelTotalSalary = new JLabel();
        labelTotalRemainingDays = new JLabel();
        labelTotalAverageSalary = new JLabel();
        labelTotalPayrollSum = new JLabel();

        // Создаем панель для размещения JLabel'ов
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel tittleLabel = new JLabel("Выписка об нетрудоспособности");
        tittleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tittleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(tittleLabel, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        centerPanel.add(new JLabel("Фамилия:"));
        centerPanel.add(labelUserSurname);
        centerPanel.add(new JLabel("Имя:"));
        centerPanel.add(labelUserName);
        centerPanel.add(new JLabel("Отчество:"));
        centerPanel.add(labelUserPatronimic);
        centerPanel.add(new JLabel("Нач. дата болезни:"));
        centerPanel.add(startIllnessDateLabel);
        centerPanel.add(new JLabel("Кон. дата болезни:"));
        centerPanel.add(endIllnessDateLabel);
        centerPanel.add(new JLabel("Кол. дней нетруд. за прошлые 6 мес.:"));
        centerPanel.add(labelTotalSickDates);
        centerPanel.add(new JLabel("Общая зарплата за 6 месяцев:"));
        centerPanel.add(labelTotalSalary);
        centerPanel.add(new JLabel("Всего рабочих дней за 6 месяцев:"));
        centerPanel.add(labelTotalRemainingDays);
        centerPanel.add(new JLabel("Общая средняя заработная плата:"));
        centerPanel.add(labelTotalAverageSalary);
        centerPanel.add(new JLabel("Общая сумма выплаты:"));
        centerPanel.add(labelTotalPayrollSum);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Размещаем панель на форме
        getContentPane().add(panel, BorderLayout.CENTER);

        // Создаем кнопки
        JButton wordButton = new JButton("Word");
        JButton excelButton = new JButton("Excel");
        JButton pdfButton = new JButton("PDF");

        // Устанавливаем цвета кнопок
        wordButton.setBackground(new Color(43, 87, 154));
        wordButton.setForeground(Color.WHITE);
        excelButton.setBackground(new Color(33, 115, 70));
        excelButton.setForeground(Color.WHITE);
        pdfButton.setBackground(new Color(179, 11, 0));
        pdfButton.setForeground(Color.WHITE);

        JButton cancelButton = new JButton("Отмена");
        cancelButton.setBackground(new Color(226, 27, 34));
        cancelButton.setForeground(new Color(255, 255, 255));

        // Настраиваем размеры окна и его положение на экране
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Создаем панель для кнопок справа
        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 1, 20, 10));
        JPanel leftButtonPanel = new JPanel(new GridLayout(2, 1, 20, 10));
        JPanel topLeftButtonPanel = new JPanel(new BorderLayout());
        JLabel saveAsLabel = new JLabel("Сохранить как:");
        saveAsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        saveAsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topLeftButtonPanel.add(saveAsLabel);
        JPanel buttomLeftButtonPanel = new JPanel(new GridLayout(1, 3, 20, 10));

        buttomLeftButtonPanel.add(wordButton);
        buttomLeftButtonPanel.add(excelButton);
        buttomLeftButtonPanel.add(pdfButton);

        leftButtonPanel.add(topLeftButtonPanel);
        leftButtonPanel.add(buttomLeftButtonPanel);

        rightButtonPanel.add(cancelButton);
        bottomPanel.add(leftButtonPanel, BorderLayout.WEST);
        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        setSize(600, 350);
        setLocationRelativeTo(null);

        // Слушатель для закрытия окна
        cancelButton.addActionListener(e -> {
            dispose();
        });

        pdfButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить в PDF");

            int userSelection = fileChooser.showSaveDialog(PayrollDetailsDialog.this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(fileToSave + ".pdf"));
                    document.open();
                    BaseFont unicodeTimesNewRonman = BaseFont.createFont("src/main/resources/fonts/TimesNewRoman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    com.itextpdf.text.Font timesNewRomanFontPdf = new com.itextpdf.text.Font(unicodeTimesNewRonman, 20, Font.BOLD);
                    BaseFont unicodeArial = BaseFont.createFont("src/main/resources/fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    com.itextpdf.text.Font arialFontPdf = new com.itextpdf.text.Font(unicodeArial, 14, Font.PLAIN);

                    Paragraph para = new Paragraph("Выписка об нетрудоспособности", timesNewRomanFontPdf);
                    para.setAlignment(Element.ALIGN_CENTER);
                    document.add(para);

                    // Отступ от таблицы
                    Paragraph spacing = new Paragraph(" ");
                    spacing.setSpacingBefore(10);
                    document.add(spacing);

                    PdfPTable table = new PdfPTable(2);
                    table.setWidthPercentage(100);

                    PdfPCell cell = new PdfPCell(new Phrase("Фамилия:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelUserSurname.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Имя:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelUserName.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Отчество:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelUserPatronimic.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Нач. дата болезни:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(startIllnessDateLabel.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Кон. дата болезни:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(endIllnessDateLabel.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Кол. дней нетруд. за прошлые 6 мес.:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelTotalSickDates.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Общая зарплата за 6 месяцев:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelTotalSalary.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Всего рабочих дней за 6 месяцев:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelTotalRemainingDays.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Общая средняя заработная плата:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelTotalAverageSalary.getText()));
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Общая сумма выплаты:", arialFontPdf));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(labelTotalPayrollSum.getText()));
                    table.addCell(cell);

                    document.add(table);
                    document.close();

                    JOptionPane.showMessageDialog(PayrollDetailsDialog.this,
                            "Файл успешно сохранен в формате PDF!", "Файл сохранен",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (FileNotFoundException | DocumentException ex) {
                    JOptionPane.showMessageDialog(PayrollDetailsDialog.this,
                            "Ошибка при сохранении файла!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    // Метод для установки значений в JLabel'ы
    public void setPayrollDetails(Object[] payrollDetails) {
        labelUserSurname.setText(payrollDetails[0].toString());
        labelUserName.setText(payrollDetails[1].toString());
        labelUserPatronimic.setText(payrollDetails[2].toString());
        startIllnessDateLabel.setText(payrollDetails[3].toString());
        endIllnessDateLabel.setText(payrollDetails[4].toString());
        labelTotalSickDates.setText(payrollDetails[5].toString());
        labelTotalSalary.setText(payrollDetails[6].toString());
        labelTotalRemainingDays.setText(payrollDetails[7].toString());
        labelTotalAverageSalary.setText(payrollDetails[8].toString());
        labelTotalPayrollSum.setText(payrollDetails[9].toString());
    }
}

