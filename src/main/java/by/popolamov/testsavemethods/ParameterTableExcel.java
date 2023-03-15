package by.popolamov.testsavemethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import org.apache.poi.xssf.usermodel.*;

/**
 * @author Denis Popolamov
 */


public class ParameterTableExcel extends JFrame implements ActionListener {

    private JLabel label1, label2, label3;
    private JTextField textField1, textField2, textField3;
    private JButton saveButton;

    public ParameterTableExcel() {
        super("Parameter Table Excel");

        label1 = new JLabel("Parameter 1:");
        label2 = new JLabel("Parameter 2:");
        label3 = new JLabel("Parameter 3:");

        textField1 = new JTextField(10);
        textField2 = new JTextField(10);
        textField3 = new JTextField(10);

        saveButton = new JButton("Save to Excel");
        saveButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(saveButton);

        this.add(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String parameter1 = textField1.getText();
            String parameter2 = textField2.getText();
            String parameter3 = textField3.getText();

            // Create a new workbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            // Create a new sheet
            XSSFSheet sheet = workbook.createSheet("Parameter Table");

            // Create header row
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Parameter");
            headerRow.createCell(1).setCellValue("Value");

            // Create data rows
            XSSFRow dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("Parameter 1");
            dataRow1.createCell(1).setCellValue(parameter1);

            XSSFRow dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue("Parameter 2");
            dataRow2.createCell(1).setCellValue(parameter2);

            XSSFRow dataRow3 = sheet.createRow(3);
            dataRow3.createCell(0).setCellValue("Parameter 3");
            dataRow3.createCell(1).setCellValue(parameter3);

            // Autosize columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            try {
                // Ask user to select a file location and name
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save As");
                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    // Save the workbook to the selected file
                    FileOutputStream fileOut = new FileOutputStream(filePath + ".xlsx");
                    workbook.write(fileOut);
                    fileOut.close();

                    System.out.println("Excel file has been saved successfully!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ParameterTableExcel();
    }
}
