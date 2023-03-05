package by.popolamov.cursework.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Denis Popolamov
 */
public class TestWindow extends JFrame {

    public TestWindow() {
        setTitle("PayRoll Calculate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        setIconImage(icon.getImage());

        // Создаем менюбар
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpenItem = new JMenuItem("Open");
        JMenuItem fileExitItem = new JMenuItem("Exit");
        fileExitItem.addActionListener(e -> System.exit(0));
//        fileExitItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
        fileMenu.add(fileOpenItem);
        fileMenu.add(fileExitItem);

        menuBar.add(fileMenu);
        JMenu helpMenu = new JMenu("Help");

        JMenuItem helpAboutItem = new JMenuItem("About");

        helpMenu.add(helpAboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // создаем панель синего цвета
        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(new Color(27, 161, 226));
        bluePanel.setPreferredSize(new Dimension(250, getHeight()));

        // добавляем надпись вверху синего блока
        JLabel label = new JLabel("БОЛЬНИЧНЫЕ");
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.NORTH);
        label.setPreferredSize(new Dimension(200, 30));
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        bluePanel.add(label, BorderLayout.NORTH);

        // добавляем картинку в середину синего блока
        ImageIcon imageIcon = new ImageIcon("C:/Users/Deokma/Downloads/icon-white.png");
        JLabel imageLabel = new JLabel(imageIcon);
        Image image = imageIcon.getImage(); // получаем объект Image из ImageIcon
        Image scaledImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH); // масштабируем изображение до нужного размера
        imageIcon.setImage(scaledImage); // устанавливаем масштабированное изображение в качестве иконки для JLabel
        imageLabel.setPreferredSize(new Dimension(200, 440));
        imageLabel.setVerticalAlignment(JLabel.TOP);
        bluePanel.add(imageLabel, BorderLayout.CENTER);

        // добавляем две кнопки внизу синего блока
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(27, 161, 226));
        JButton aboutAuthorButton = new JButton("Об авторе");
        aboutAuthorButton.addActionListener(e -> new AboutAuthor(this));

        JButton button2 = new JButton("О программе");
        aboutAuthorButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        button2.setFont(new Font("Helvetica", Font.BOLD, 20));
        //  button2.setPreferredSize();
        aboutAuthorButton.setBackground(new Color(27, 161, 226));
        button2.setBackground(new Color(27, 161, 226));
        aboutAuthorButton.setForeground(new Color(255, 255, 255));
        button2.setForeground(new Color(255, 255, 255));

        aboutAuthorButton.setBorderPainted(false);
        button2.setBorderPainted(false);

        aboutAuthorButton.setHorizontalAlignment(SwingConstants.LEFT);
        button2.setHorizontalAlignment(SwingConstants.LEFT);

        //button1.setPreferredSize(new Dimension(200, 10));
        //button1.setSize(2000, 200);
        aboutAuthorButton.setPreferredSize(new Dimension(260, 30));
        button2.setPreferredSize(new Dimension(260, 30));
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(aboutAuthorButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // отступ между кнопками

        buttonPanel.add(button2);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.setPreferredSize(new Dimension(250, 80));
        bluePanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        // создаем панель, которая занимает оставшееся пространство
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
//        JButton addWorkerButton = new JButton("Добавить");
//        mainPanel.add(addWorkerButton,BorderLayout.CENTER);
//        addWorkerButton.addActionListener(e -> new NewWorker(this));

        JTable table = new JTable();

        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Фамилия", "Имя", "Отчество", "Период болезни", "Сумма"}));

        JPanel centerPanel = new JPanel(new BorderLayout());

        JButton addWorkerButton = new JButton("Добавить");
        addWorkerButton.addActionListener(e -> new NewWorker(this));
        JButton deleteButtom = new JButton("Удалить");
        JButton toFileButtom = new JButton("В файл");

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel tableButtonPanel = new JPanel(new GridLayout(1, 2));
        tableButtonPanel.add(addWorkerButton);
        tableButtonPanel.add(deleteButtom);

        topPanel.add(tableButtonPanel, BorderLayout.WEST);
        topPanel.add(toFileButtom, BorderLayout.EAST);

        centerPanel.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        centerPanel.add(tablePanel);


        //topPanel.add(leftPanel, BorderLayout.WEST);
        //topPanel.add(toFileButtom, BorderLayout.EAST);


        // добавляем панели на окно
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(bluePanel, BorderLayout.WEST);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        //getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        TestWindow window = new TestWindow();
        window.setVisible(true);
    }
}

