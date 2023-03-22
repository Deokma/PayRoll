//package by.popolamov.testsavemethods;
//
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.math.BigInteger;
//
//import org.apache.poi.xwpf.usermodel.*;
//import org.apache.poi.*;
//
///**
// * @author Denis Popolamov
// */
//
//
//public class ParameterTableWord extends JFrame implements ActionListener {
//    private JLabel label1, label2, label3;
//    private JTextField textField1, textField2, textField3;
//    private JButton saveButton;
//
//    public ParameterTableWord() {
//        super("Parameter Table Word");
//
//        label1 = new JLabel("Parameter 1:");
//        label2 = new JLabel("Parameter 2:");
//        label3 = new JLabel("Parameter 3:");
//
//        textField1 = new JTextField(10);
//        textField2 = new JTextField(10);
//        textField3 = new JTextField(10);
//
//        saveButton = new JButton("Save to Word");
//        saveButton.addActionListener(this);
//
//        JPanel panel = new JPanel(new GridLayout(4, 2));
//        panel.add(label1);
//        panel.add(textField1);
//        panel.add(label2);
//        panel.add(textField2);
//        panel.add(label3);
//        panel.add(textField3);
//        panel.add(saveButton);
//
//        this.add(panel);
//        this.pack();
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setVisible(true);
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == saveButton) {
//            String parameter1 = textField1.getText();
//            String parameter2 = textField2.getText();
//            String parameter3 = textField3.getText();
//
//            try {
//                // Ask user to select a file location and name
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setDialogTitle("Save As");
//                int userSelection = fileChooser.showSaveDialog(this);
//                if (userSelection == JFileChooser.APPROVE_OPTION) {
//                    File fileToSave = fileChooser.getSelectedFile();
//                    String filePath = fileToSave.getAbsolutePath();
//
//                    // Create a new document
//                    XWPFDocument document = new XWPFDocument();
//
//                    // Create a table with 2 columns and 4 rows
//                    XWPFTable table = document.createTable(4, 2);
//
//                    // Create header row
//                    XWPFTableRow headerRow = table.getRow(0);
//                    headerRow.getCell(0).setText("Parameter");
//                    headerRow.getCell(1).setText("Value");
//
//                    // Create data rows
//                    XWPFTableRow dataRow1 = table.getRow(1);
//                    dataRow1.getCell(0).setText("Parameter 1");
//                    dataRow1.getCell(1).setText(parameter1);
//
//                    XWPFTableRow dataRow2 = table.getRow(2);
//                    dataRow2.getCell(0).setText("Parameter 2");
//                    dataRow2.getCell(1).setText(parameter2);
//
//                    XWPFTableRow dataRow3 = table.getRow(3);
//                    dataRow3.getCell(0).setText("Parameter 3");
//                    dataRow3.getCell(1).setText(parameter3);
//
//                    // Autosize columns
//                    table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(2000));
//                    table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(6000));
//                    for (XWPFTableRow row : table.getRows()) {
//                        for (XWPFTableCell cell : row.getTableCells()) {
//                            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3000));
//                        }
//                    }
//
//                    // Save the document to the selected file
//                    FileOutputStream fileOut = new FileOutputStream(filePath + ".docx");
//                    document.write(fileOut);
//                    fileOut.close();
//
//                    System.out.println("Word file has been saved successfully!");
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        // create a new instance of ParameterTableWord
//        ParameterTableWord parameterTableWord = new ParameterTableWord();
//
//        // make the frame visible
//        parameterTableWord.setVisible(true);
//    }
//
//
//}
