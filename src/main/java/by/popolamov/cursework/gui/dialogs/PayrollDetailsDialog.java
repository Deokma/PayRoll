package by.popolamov.cursework.gui.dialogs;


import by.popolamov.cursework.connect.DBManager;
import by.popolamov.cursework.listeners.ExcelSaveListener;
import by.popolamov.cursework.listeners.PDFSaveListener;
import by.popolamov.cursework.listeners.WordSaveListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Диалоговое окно с деталями расчётов выплат
 *
 * @author Denis Popolamov
 */
public class PayrollDetailsDialog extends JDialog {
    private final Object[] payrollDetails;
    long payroll_id;
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
    List<Integer> monthDaysList;
    List<Double> salaryList;
    List<Integer> sickMonthDaysList;
    List<Integer> remainingCalendarDaysList;
    String currentMonth;
    Double eightyPecentSalary;
    Double hundredPercentSalary;
    int numberOfIllnessDays;
    int totalMonthDays;

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

        JPanel pnlDeletePayroll = new JPanel(new BorderLayout());
        ImageIcon deleteIcon = new ImageIcon("src/main/resources/images/delete-icon.png");
        JButton btnDelete = new JButton(deleteIcon);

        //btnDelete.setBorderPainted(false); // скрыть границы кнопки
        btnDelete.setContentAreaFilled(false); // убрать заливку кнопки
        btnDelete.setFocusPainted(false); // убрать выделение кнопки при фокусе
        btnDelete.setPreferredSize(new Dimension(btnDelete.getIcon().getIconWidth() + 5, btnDelete.getIcon().getIconHeight() + 5)); // установить размеры кнопки равными размерам изображения

        pnlDeletePayroll.add(btnDelete, BorderLayout.SOUTH);
        getContentPane().add(pnlDeletePayroll, BorderLayout.EAST);
        pnlDeletePayroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        btnPDF.addActionListener(e -> {
            PDFSaveListener btnPDFListener = new PDFSaveListener(this, lblUserSurname,
                    lblUserName, lblUserPatronimic,
                    lblStartIllnessDate, lblEndIllnessDate,
                    lblTotalSickDates, lblTotalPayrollSum,
                    averageSalaryList, monthList,
                    salaryList, remainingCalendarDaysList,
                    currentMonth, eightyPecentSalary,
                    hundredPercentSalary, numberOfIllnessDays);
            btnPDFListener.actionPerformed(e);
        });
        btnWord.addActionListener(e -> {
            WordSaveListener wordSaveListener = new WordSaveListener(this, lblUserSurname,
                    lblUserName, lblUserPatronimic,
                    lblStartIllnessDate, lblEndIllnessDate,
                    lblTotalSickDates, lblTotalPayrollSum,
                    averageSalaryList, monthList,
                    salaryList, remainingCalendarDaysList,
                    currentMonth, eightyPecentSalary,
                    hundredPercentSalary, numberOfIllnessDays);
            wordSaveListener.actionPerformed(e);
        });
        btnExcel.addActionListener(e -> {
            ExcelSaveListener excelSaveListener = new ExcelSaveListener(this, lblUserSurname,
                    lblUserName, lblUserPatronimic,
                    lblStartIllnessDate, lblEndIllnessDate,
                    lblTotalSickDates, lblTotalSalary, lblTotalRemainingDays,
                    lblTotalAverageSalary, lblTotalPayrollSum,
                    averageSalaryList, monthList,
                    salaryList, remainingCalendarDaysList,
                    currentMonth, eightyPecentSalary,
                    hundredPercentSalary, numberOfIllnessDays, sickMonthDaysList,
                    monthDaysList, totalMonthDays);
            excelSaveListener.actionPerformed(e);
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить элемент?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        db.deletePayrollData(payroll_id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                }
            }
        });
    }

    // Метод для установки значений в JLabel'ы
    public void setPayrollDetails(Object[] payrollDetails) {

        payroll_id = (long) payrollDetails[0];
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
        numberOfIllnessDays = Integer.valueOf(payrollDetails[14].toString());
        totalMonthDays = Integer.valueOf(payrollDetails[15].toString());

        averageSalaryList = db.getAverageSalaryByPayrollId(payroll_id);
        monthList = db.getPayrollMonthsByPayrollId(payroll_id);
        monthDaysList = db.getMonthDaysByPayrollId(payroll_id);
        salaryList = db.getSalaryByPayrollId(payroll_id);
        sickMonthDaysList = db.getSickMonthDaysByPayrollId(payroll_id);
        remainingCalendarDaysList = db.getRemainingDaysByPayrollId(payroll_id);
    }
}