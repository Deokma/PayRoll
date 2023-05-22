package by.popolamov.coursework.gui.windows;

import by.popolamov.coursework.connect.DBManager;
import by.popolamov.coursework.gui.dialogs.AboutAuthorDialog;
import by.popolamov.coursework.gui.dialogs.AboutProgramDialog;
import by.popolamov.coursework.gui.dialogs.HelpDialog;
import by.popolamov.coursework.gui.dialogs.NewPayrollDialog;
import by.popolamov.coursework.utils.ButtonColumn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Главное окно
 *
 * @author Denis Popolamov
 */
public class MainWindow extends JFrame {
    JTable tblPayroll = new JTable();
    DBManager db = new DBManager();

    public MainWindow() throws ClassNotFoundException {
        setTitle("PayRoll Calculate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 620);
        setResizable(false);
        setLocationRelativeTo(null);

        // Установка иконки приложения
        ImageIcon icon =
                new ImageIcon("src/main/resources/images/icon.png");
        //new ImageIcon(ClassLoader.getSystemResource("resources/images/icon.png"));

        setIconImage(icon.getImage());

        // Создание menuBar и элементов меню
        JMenuBar mnuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem mniFileExit = new JMenuItem("Exit");
        mniFileExit.addActionListener(e -> System.exit(0));

        fileMenu.add(mniFileExit);
        mnuBar.add(fileMenu);

        JMenu mnuHelp = new JMenu("Help");
        JMenuItem mniHelpHelp = new JMenuItem("Help");
        JMenuItem mniHelpAbout = new JMenuItem("About");
        mniHelpHelp.addActionListener(e -> new HelpDialog(this));
        mniHelpAbout.addActionListener(e -> new AboutProgramDialog(this));
        mnuHelp.add(mniHelpHelp);
        mnuBar.add(mnuHelp);

        setJMenuBar(mnuBar);

        // Создание боковой панели синего цвета
        JPanel pnlLeftBlue = new JPanel();
        pnlLeftBlue.setBackground(new Color(27, 161, 226));
        pnlLeftBlue.setPreferredSize(new Dimension(250, getHeight()));

        // добавляем надпись вверху синего блока
        JLabel lblTitle = new JLabel("БОЛЬНИЧНЫЕ");
        lblTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setVerticalAlignment(JLabel.NORTH);
        lblTitle.setPreferredSize(new Dimension(200, 30));
        lblTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
        pnlLeftBlue.add(lblTitle, BorderLayout.NORTH);

        // добавляем картинку в середину синего блока
        ImageIcon imageIcon =
                new ImageIcon("src/main/resources/images/icon-white.png");
        //new ImageIcon(ClassLoader.getSystemResource("resources/images/icon-white.png"));
        JLabel lblImage = new JLabel(imageIcon);
        Image image = imageIcon.getImage(); // получаем объект Image из ImageIcon
        Image scaledImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH);
        // масштабируем изображение до нужного размера
        imageIcon.setImage(scaledImage); // устанавливаем масштабированное изображение в качестве иконки для JLabel
        lblImage.setPreferredSize(new Dimension(200, 440));
        lblImage.setVerticalAlignment(JLabel.TOP);
        pnlLeftBlue.add(lblImage, BorderLayout.CENTER);

        // добавляем две кнопки внизу синего блока
        JPanel pnlButtonsOnBlue = new JPanel();
        pnlButtonsOnBlue.setBackground(new Color(27, 161, 226));
        JButton btnAboutAuthor = new JButton("Об авторе");

        ImageIcon iconAboutAuthorButton =
                new ImageIcon("src/main/resources/images/about-author-icon.png");
        //new ImageIcon(ClassLoader.getSystemResource("resources/images/about-author-icon.png"));
        Image aboutAuthorImage =
                iconAboutAuthorButton.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        // масштабирование картинки до 50x50
        ImageIcon iconScaledAboutAuthorButton =
                new ImageIcon(aboutAuthorImage); // создание нового ImageIcon с измененным размером
        btnAboutAuthor.setIcon(iconScaledAboutAuthorButton);

        JButton btnAboutProgram = new JButton("О программе");

        ImageIcon iconAboutProgramButton =
                new ImageIcon("src/main/resources/images/about-program-icon.png");
        //new ImageIcon(ClassLoader.getSystemResource("resources/images/about-program-icon.png"));
        Image aboutProgramImage =
                iconAboutProgramButton.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        // масштабирование картинки до 50x50
        ImageIcon scaledAboutProgramButtonIcon =
                new ImageIcon(aboutProgramImage); // создание нового ImageIcon с измененным размером
        btnAboutProgram.setIcon(scaledAboutProgramButtonIcon);
        btnAboutProgram.addActionListener(e -> new AboutProgramDialog(this));

        btnAboutProgram.setFont(new Font("Helvetica", Font.BOLD, 20));
        btnAboutProgram.setBackground(new Color(27, 161, 226));
        btnAboutProgram.setForeground(new Color(255, 255, 255));
        btnAboutProgram.setBorderPainted(false);
        btnAboutProgram.setHorizontalAlignment(SwingConstants.LEFT);
        btnAboutProgram.setPreferredSize(new Dimension(260, 30));

        btnAboutAuthor.setFont(new Font("Helvetica", Font.BOLD, 20));
        btnAboutAuthor.setBackground(new Color(27, 161, 226));
        btnAboutAuthor.setForeground(new Color(255, 255, 255));
        btnAboutAuthor.setBorderPainted(false);
        btnAboutAuthor.setHorizontalAlignment(SwingConstants.LEFT);
        btnAboutAuthor.setPreferredSize(new Dimension(260, 30));

        pnlButtonsOnBlue.add(Box.createVerticalGlue());
        pnlButtonsOnBlue.add(btnAboutAuthor);
        pnlButtonsOnBlue.add(Box.createVerticalStrut(10)); // отступ между кнопками

        pnlButtonsOnBlue.add(btnAboutProgram);
        pnlButtonsOnBlue.add(Box.createVerticalGlue());
        pnlButtonsOnBlue.setPreferredSize(new Dimension(250, 80));
        pnlLeftBlue.add(pnlButtonsOnBlue, BorderLayout.SOUTH);

        DefaultTableModel dtModel = db.getAllPayrolls();
        tblPayroll.setModel(dtModel);
        tblPayroll.setRowHeight(30);

        TableColumnModel columnModel = tblPayroll.getColumnModel();
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
        ButtonColumn buttonColumn = new ButtonColumn(tblPayroll, viewAction, 7);

        //Запрет на изменение таблицы
        tblPayroll.getTableHeader().setReorderingAllowed(false);
        tblPayroll.getTableHeader().setResizingAllowed(false);
        tblPayroll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel pnlCenter = new JPanel(new BorderLayout());

        JButton btnAddWorker = new JButton("Добавить");
        btnAddWorker.setBackground(new Color(27, 161, 226));
        btnAddWorker.setForeground(new Color(255, 255, 255));
        btnAddWorker.addActionListener(e -> {
            try {
                new NewPayrollDialog(this);
            } catch (NoClassDefFoundError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Не обнаружен компонент: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
        JButton btnUpdate = new JButton("Обновить");
        btnUpdate.setBackground(new Color(27, 161, 226));
        btnUpdate.setForeground(new Color(255, 255, 255));

        JPanel pnlTop = new JPanel(new BorderLayout());
        JPanel pnlTableButton = new JPanel(new GridLayout(1, 2));
        pnlTableButton.add(btnAddWorker);
        pnlTableButton.add(btnUpdate);

        pnlTop.add(pnlTableButton, BorderLayout.WEST);
        pnlCenter.add(pnlTop, BorderLayout.NORTH);

        JPanel pnlTable = new JPanel();
        pnlTable.add(tblPayroll.getTableHeader(), BorderLayout.NORTH);
        pnlTable.add(tblPayroll, BorderLayout.CENTER);

        pnlCenter.add(new JScrollPane(tblPayroll), BorderLayout.CENTER);

        tblPayroll.setFillsViewportHeight(true);
        // Создаем отступы и устанавливаем их для центральной панели
        pnlCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

        // добавляем панели на окно
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlLeftBlue, BorderLayout.WEST);
        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        btnAboutAuthor.addActionListener(e -> {
            try {
                new AboutAuthorDialog(this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Получение модели данных для таблицы
                DefaultTableModel dtModel = db.getAllPayrolls();
                // Установка модели данных в таблицу
                tblPayroll.setModel(dtModel);
                // Установка высоты строки
                tblPayroll.setRowHeight(30);
                // Получение модели столбцов таблицы
                TableColumnModel columnModel = tblPayroll.getColumnModel();
                // Настройка предпочтительной ширины столбцов
                columnModel.getColumn(0).setPreferredWidth(10);
                columnModel.getColumn(7).setPreferredWidth(10);
                // Настройка редактирования ячеек
                TableColumn editableColumn = columnModel.getColumn(7);
                editableColumn.setCellEditor(new DefaultCellEditor(new JTextField()));
                // Добавление кнопок в столбец
                Action viewAction = new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        Object[] rowData = (Object[]) e.getSource();
                        // Открыть окно с данными из строки
                        db.getPayrollDetails((int) rowData[0]);
                    }
                };
                ButtonColumn buttonColumn = new ButtonColumn(tblPayroll, viewAction, 7);
                tblPayroll.getTableHeader().setReorderingAllowed(false);
                tblPayroll.getTableHeader().setResizingAllowed(false);
                tblPayroll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        });

    }
}

