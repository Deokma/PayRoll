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
import java.io.IOException;

public class MainWindow extends JFrame {
    JTable tbl = new JTable();
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
        JMenuBar mnu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem mniFileOpen = new JMenuItem("Open");
        JMenuItem mniFileExit = new JMenuItem("Exit");
        mniFileExit.addActionListener(e -> System.exit(0));

        fileMenu.add(mniFileOpen);
        fileMenu.add(mniFileExit);

        mnu.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem mniHelpAbout = new JMenuItem("About");

        helpMenu.add(mniHelpAbout);
        mnu.add(helpMenu);
        setJMenuBar(mnu);

        // создаем панель синего цвета
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
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/icon-white.png");
        JLabel lblImage = new JLabel(imageIcon);
        Image image = imageIcon.getImage(); // получаем объект Image из ImageIcon
        Image scaledImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH); // масштабируем изображение до нужного размера
        imageIcon.setImage(scaledImage); // устанавливаем масштабированное изображение в качестве иконки для JLabel
        lblImage.setPreferredSize(new Dimension(200, 440));
        lblImage.setVerticalAlignment(JLabel.TOP);
        pnlLeftBlue.add(lblImage, BorderLayout.CENTER);

        // добавляем две кнопки внизу синего блока
        JPanel pnlButtonsOnBlue = new JPanel();
        pnlButtonsOnBlue.setBackground(new Color(27, 161, 226));
        JButton btnAboutAuthor = new JButton("Об авторе");
        ImageIcon iconAboutAuthorButton = new ImageIcon("src/main/resources/images/about-author-icon.png");
        Image aboutAuthorImage = iconAboutAuthorButton.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon iconScaledAboutAuthorButton = new ImageIcon(aboutAuthorImage); // создание нового ImageIcon с измененным размером
        btnAboutAuthor.setIcon(iconScaledAboutAuthorButton);

        btnAboutAuthor.addActionListener(e -> {
            try {
                new AboutAuthor(this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton btnAboutProgram = new JButton("О программе");
        ImageIcon iconAboutProgramButton = new ImageIcon("src/main/resources/images/about-program-icon.png");
        Image aboutProgramImage = iconAboutProgramButton.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledAboutProgramButtonIcon = new ImageIcon(aboutProgramImage); // создание нового ImageIcon с измененным размером
        btnAboutProgram.setIcon(scaledAboutProgramButtonIcon);
        btnAboutProgram.addActionListener(e -> new AboutProgram(this));

        btnAboutAuthor.setFont(new Font("Helvetica", Font.BOLD, 20));
        btnAboutProgram.setFont(new Font("Helvetica", Font.BOLD, 20));
        //  button2.setPreferredSize();
        btnAboutAuthor.setBackground(new Color(27, 161, 226));
        btnAboutProgram.setBackground(new Color(27, 161, 226));
        btnAboutAuthor.setForeground(new Color(255, 255, 255));
        btnAboutProgram.setForeground(new Color(255, 255, 255));

        btnAboutAuthor.setBorderPainted(false);
        btnAboutProgram.setBorderPainted(false);

        btnAboutAuthor.setHorizontalAlignment(SwingConstants.LEFT);
        btnAboutProgram.setHorizontalAlignment(SwingConstants.LEFT);

        //button1.setPreferredSize(new Dimension(200, 10));
        //button1.setSize(2000, 200);
        btnAboutAuthor.setPreferredSize(new Dimension(260, 30));
        btnAboutProgram.setPreferredSize(new Dimension(260, 30));
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        pnlButtonsOnBlue.add(Box.createVerticalGlue());
        pnlButtonsOnBlue.add(btnAboutAuthor);
        pnlButtonsOnBlue.add(Box.createVerticalStrut(10)); // отступ между кнопками

        pnlButtonsOnBlue.add(btnAboutProgram);
        pnlButtonsOnBlue.add(Box.createVerticalGlue());
        pnlButtonsOnBlue.setPreferredSize(new Dimension(250, 80));
        pnlLeftBlue.add(pnlButtonsOnBlue, BorderLayout.SOUTH);

        DefaultTableModel dtModel = db.getAllPayrolls();
        tbl.setModel(dtModel);
        tbl.setRowHeight(30);

        TableColumnModel columnModel = tbl.getColumnModel();
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
        ButtonColumn buttonColumn = new ButtonColumn(tbl, viewAction, 7);

        //Запрет на изменение таблицы
        //table.setEnabled(false);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.getTableHeader().setResizingAllowed(false);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // table.setModel(new DefaultTableModel();

        JPanel pnlCenter = new JPanel(new BorderLayout());

        JButton btnAddWorker = new JButton("Добавить");
        btnAddWorker.setBackground(new Color(27, 161, 226));
        btnAddWorker.setForeground(new Color(255, 255, 255));
        btnAddWorker.addActionListener(e -> new NewWorker(this));
        JButton btnDelete = new JButton("Удалить");
        btnDelete.setBackground(new Color(27, 161, 226));
        btnDelete.setForeground(new Color(255, 255, 255));

        JPanel pnlTop = new JPanel(new BorderLayout());
        JPanel pnlTableButton = new JPanel(new GridLayout(1, 2));
        pnlTableButton.add(btnAddWorker);
        pnlTableButton.add(btnDelete);

        pnlTop.add(pnlTableButton, BorderLayout.WEST);
        // pnlTop.add(toFileButtom, BorderLayout.EAST);

        pnlCenter.add(pnlTop, BorderLayout.NORTH);

        JPanel pnlTable = new JPanel();
        pnlTable.add(tbl.getTableHeader(), BorderLayout.NORTH);
        pnlTable.add(tbl, BorderLayout.CENTER);

        //pnlCenter.add(pnlTable);
        // добавляем таблицу на центральную панель
        pnlCenter.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // устанавливаем размеры таблицы
        tbl.setFillsViewportHeight(true);
        // Создаем отступы и устанавливаем их для центральной панели
        pnlCenter.setBorder(new EmptyBorder(20, 20, 20, 20));
        //pnlTop.add(leftPanel, BorderLayout.WEST);
        //pnlTop.add(toFileButtom, BorderLayout.EAST);

        // добавляем панели на окно
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlLeftBlue, BorderLayout.WEST);
        getContentPane().add(pnlCenter, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}

