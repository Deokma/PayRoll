//package by.popolamov.testsavemethods;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//
///**
// * @author Denis Popolamov
// */
//
//
//public class TestTablePDF extends JFrame implements ActionListener {
//
//    private JLabel label1, label2, label3;
//    private JTextField textField1, textField2, textField3;
//    private JButton saveButton;
//
//    public TestTablePDF() {
//        super("Parameter Table PDF");
//
//        label1 = new JLabel("Parameter 1:");
//        label2 = new JLabel("Parameter 2:");
//        label3 = new JLabel("Parameter 3:");
//
//        textField1 = new JTextField(10);
//        textField2 = new JTextField(10);
//        textField3 = new JTextField(10);
//
//        saveButton = new JButton("Save to PDF");
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
//            // создаем диалоговое окно для выбора файла
//            JFileChooser fileChooser = new JFileChooser();
//            int result = fileChooser.showSaveDialog(this);
//
//            if (result == JFileChooser.APPROVE_OPTION) {
//                File selectedFile = fileChooser.getSelectedFile();
//
//                Document document = new Document();
//                try {
//                    // сохраняем файл с выбранным именем и расширением .pdf
//                    PdfWriter.getInstance(document, new FileOutputStream(selectedFile.getAbsolutePath() + ".pdf"));
//                    document.open();
//
//                    PdfPTable table = new PdfPTable(2);
//                    table.addCell("Parameter");
//                    table.addCell("Value");
//                    table.addCell("Parameter 1");
//                    table.addCell(parameter1);
//                    table.addCell("Parameter 2");
//                    table.addCell(parameter2);
//                    table.addCell("Parameter 3");
//                    table.addCell(parameter3);
//
//                    document.add(table);
//
//                    document.close();
//
//                    JOptionPane.showMessageDialog(this, "File saved successfully!");
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(this, "Error while saving file!");
//                }
//            }
//        }
//    }
//
//
//    public static void main(String[] args) {
//        new TestTablePDF();
//    }
//}
