package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */

import by.popolamov.cursework.connect.DBManager;
import by.popolamov.cursework.utils.ButtonColumn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
    JTable table = new JTable();
    DBManager db = new DBManager();

    public MainWindow() {
        setTitle("PayRoll Calculate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png");
        setIconImage(icon.getImage());

        // Создаем менюбар
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpenItem = new JMenuItem("Open");
        JMenuItem fileExitItem = new JMenuItem("Exit");
        fileExitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(fileOpenItem);
        fileMenu.add(fileExitItem);

        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpAboutItem = new JMenuItem("About");

        helpMenu.add(helpAboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // создаем панель синего цвета
        JPanel leftBluePanel = new JPanel();
        leftBluePanel.setBackground(new Color(27, 161, 226));
        leftBluePanel.setPreferredSize(new Dimension(250, getHeight()));

        // добавляем надпись вверху синего блока
        JLabel label = new JLabel("БОЛЬНИЧНЫЕ");
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.NORTH);
        label.setPreferredSize(new Dimension(200, 30));
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        leftBluePanel.add(label, BorderLayout.NORTH);

        // добавляем картинку в середину синего блока
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/icon-white.png");
        JLabel imageLabel = new JLabel(imageIcon);
        Image image = imageIcon.getImage(); // получаем объект Image из ImageIcon
        Image scaledImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH); // масштабируем изображение до нужного размера
        imageIcon.setImage(scaledImage); // устанавливаем масштабированное изображение в качестве иконки для JLabel
        imageLabel.setPreferredSize(new Dimension(200, 440));
        imageLabel.setVerticalAlignment(JLabel.TOP);
        leftBluePanel.add(imageLabel, BorderLayout.CENTER);

        // добавляем две кнопки внизу синего блока
        JPanel buttonsOnBluePanel = new JPanel();
        buttonsOnBluePanel.setBackground(new Color(27, 161, 226));
        JButton aboutAuthorButton = new JButton("Об авторе");
        ImageIcon aboutAuthorButtonIcon = new ImageIcon("src/main/resources/images/about-author-icon.png");
        Image aboutAuthorImage = aboutAuthorButtonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledAboutAuthorButtonIcon = new ImageIcon(aboutAuthorImage); // создание нового ImageIcon с измененным размером
        aboutAuthorButton.setIcon(scaledAboutAuthorButtonIcon);

        aboutAuthorButton.addActionListener(e -> new AboutAuthor(this));

        JButton aboutProgramButton = new JButton("О программе");
        ImageIcon aboutProgramButtonIcon = new ImageIcon("src/main/resources/images/about-program-icon.png");
        Image aboutProgramImage = aboutProgramButtonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledAboutProgramButtonIcon = new ImageIcon(aboutProgramImage); // создание нового ImageIcon с измененным размером
        aboutProgramButton.setIcon(scaledAboutProgramButtonIcon);
        aboutProgramButton.addActionListener(e -> new AboutProgram(this));

        aboutAuthorButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        aboutProgramButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        //  button2.setPreferredSize();
        aboutAuthorButton.setBackground(new Color(27, 161, 226));
        aboutProgramButton.setBackground(new Color(27, 161, 226));
        aboutAuthorButton.setForeground(new Color(255, 255, 255));
        aboutProgramButton.setForeground(new Color(255, 255, 255));

        aboutAuthorButton.setBorderPainted(false);
        aboutProgramButton.setBorderPainted(false);

        aboutAuthorButton.setHorizontalAlignment(SwingConstants.LEFT);
        aboutProgramButton.setHorizontalAlignment(SwingConstants.LEFT);

        //button1.setPreferredSize(new Dimension(200, 10));
        //button1.setSize(2000, 200);
        aboutAuthorButton.setPreferredSize(new Dimension(260, 30));
        aboutProgramButton.setPreferredSize(new Dimension(260, 30));
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonsOnBluePanel.add(Box.createVerticalGlue());
        buttonsOnBluePanel.add(aboutAuthorButton);
        buttonsOnBluePanel.add(Box.createVerticalStrut(10)); // отступ между кнопками

        buttonsOnBluePanel.add(aboutProgramButton);
        buttonsOnBluePanel.add(Box.createVerticalGlue());
        buttonsOnBluePanel.setPreferredSize(new Dimension(250, 80));
        leftBluePanel.add(buttonsOnBluePanel, BorderLayout.SOUTH);

        DefaultTableModel model = db.getAllEmployees();
        table.setModel(model);
        table.setRowHeight(30);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn editableColumn = columnModel.getColumn(7);
        editableColumn.setCellEditor(new DefaultCellEditor(new JTextField()));
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(7).setPreferredWidth(10); // устанавливаем ширину 10 пикселей

// разрешаем только выбранный столбец для редактирования
        Action viewAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Object[] rowData = (Object[]) e.getSource();
                // Открыть окно с данными из строки
                db.getPayrollDetails((int) rowData[0]);
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(table, viewAction, 7);

        //Запрет на изменение таблицы
        //table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // table.setModel(new DefaultTableModel();

        JPanel centerPanel = new JPanel(new BorderLayout());

        JButton addWorkerButton = new JButton("Добавить");
        addWorkerButton.setBackground(new Color(27, 161, 226));
        addWorkerButton.setForeground(new Color(255, 255, 255));
        addWorkerButton.addActionListener(e -> new NewWorker(this));
        JButton deleteButtom = new JButton("Удалить");
        deleteButtom.setBackground(new Color(27, 161, 226));
        deleteButtom.setForeground(new Color(255, 255, 255));

        JButton toFileButtom = new JButton("В файл");
        toFileButtom.setBackground(new Color(27, 161, 226));
        toFileButtom.setForeground(new Color(255, 255, 255));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel tableButtonPanel = new JPanel(new GridLayout(1, 2));
        tableButtonPanel.add(addWorkerButton);
        tableButtonPanel.add(deleteButtom);

        topPanel.add(tableButtonPanel, BorderLayout.WEST);
        // topPanel.add(toFileButtom, BorderLayout.EAST);

        centerPanel.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        //centerPanel.add(tablePanel);
        // добавляем таблицу на центральную панель
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // устанавливаем размеры таблицы
        table.setFillsViewportHeight(true);
        // Создаем отступы и устанавливаем их для центральной панели
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        //topPanel.add(leftPanel, BorderLayout.WEST);
        //topPanel.add(toFileButtom, BorderLayout.EAST);

        // добавляем панели на окно
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(leftBluePanel, BorderLayout.WEST);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}

