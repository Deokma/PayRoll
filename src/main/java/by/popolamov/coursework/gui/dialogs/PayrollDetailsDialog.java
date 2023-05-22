package by.popolamov.coursework.gui.dialogs;

import by.popolamov.coursework.connect.DBManager;
import by.popolamov.coursework.listeners.ExcelSaveListener;
import by.popolamov.coursework.listeners.PDFSaveListener;
import by.popolamov.coursework.listeners.WordSaveListener;
import by.popolamov.coursework.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Диалоговое окно с деталями расчётов выплат
 *
 * @author Denis Popolamov
 */
public class PayrollDetailsDialog extends JDialog {
    long payrollId;
    AverageSalary averageSalary;
    PayrollDetails payrollDetails;
    PayrollMonths payrollMonths;
    Salary salary;
    SickMonthDays sickMonthDays;
    DBManager db = new DBManager();

    public PayrollDetailsDialog(Frame parent, PayrollDetails payrollDetails,
                                AverageSalary averageSalary, PayrollMonths payrollMonths,
                                Salary salary, SickMonthDays sickMonthDays) {
        super(parent, "Детали выплаты", true);
        this.payrollDetails = payrollDetails;
        this.averageSalary = averageSalary;
        this.payrollMonths = payrollMonths;
        this.salary = salary;
        this.sickMonthDays = sickMonthDays;
        initComponents();

        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        try {
            payrollId = payrollDetails.getPayrollId();
            // Создаем панель для размещения JLabel'ов
            JPanel pnlMain = new JPanel(new BorderLayout());
            pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
            JPanel pnlTop = new JPanel(new BorderLayout());
            JLabel tittleLabel = new JLabel("Выписка об нетрудоспособности");
            tittleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            tittleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pnlTop.add(tittleLabel, BorderLayout.CENTER);
            pnlMain.add(pnlTop, BorderLayout.NORTH);
            JPanel centerPanel = new JPanel(new GridLayout(10, 2, 5, 5));
            Font mainFont = new Font("Helvetica", Font.PLAIN, 14);
            centerPanel.setFont(mainFont);
            centerPanel.add(new JLabel("Фамилия: "
                    + payrollDetails.getUserSurName()));
            centerPanel.add(new JLabel("Имя: "
                    + payrollDetails.getUserName()));
            centerPanel.add(new JLabel("Отчество: "
                    + payrollDetails.getUserPatronymic()));
            centerPanel.add(new JLabel("Нач. дата болезни: "
                    + payrollDetails.getStartIllnessDate()));
            centerPanel.add(new JLabel("Кон. дата болезни: "
                    + payrollDetails.getEndIllnessDate()));
            centerPanel.add(new JLabel("Кол. дней нетруд. за прошлые 6 мес.: "
                    + payrollDetails.getTotalSickDates()));
            centerPanel.add(new JLabel("Общая зарплата за 6 месяцев: "
                    + payrollDetails.getTotalSalary()));
            centerPanel.add(new JLabel("Всего рабочих дней за 6 месяцев: "
                    + payrollDetails.getTotalRemainingDays()));
            centerPanel.add(new JLabel("Общая средняя заработная плата: "
                    + payrollDetails.getTotalAverageSalary()));
            centerPanel.add(new JLabel("Общая сумма выплаты: "
                    + payrollDetails.getTotalPayrollSum()));
            pnlMain.add(centerPanel, BorderLayout.CENTER);

            // Размещаем панель на форме
            getContentPane().add(pnlMain, BorderLayout.CENTER);

            JPanel pnlDeletePayroll = new JPanel(new BorderLayout());
            ImageIcon deleteIcon = new ImageIcon("src/main/resources/images/delete-icon.png");
            //ImageIcon deleteIcon = new ImageIcon(ClassLoader.getSystemResource("resources/images/delete-icon.png"));
            JButton btnDelete = new JButton(deleteIcon);

            //btnDelete.setBorderPainted(false); // скрыть границы кнопки
            btnDelete.setContentAreaFilled(false); // убрать заливку кнопки
            btnDelete.setFocusPainted(false); // убрать выделение кнопки при фокусе
            btnDelete.setPreferredSize(
                    new Dimension(btnDelete.getIcon().getIconWidth() + 5,
                            btnDelete.getIcon().getIconHeight() + 5));
            // установить размеры кнопки равным размерам изображения

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
                try {
                    PDFSaveListener btnPDFListener = new PDFSaveListener(this, averageSalary,
                            payrollDetails, payrollMonths, salary, sickMonthDays);
                    btnPDFListener.actionPerformed(e);
                } catch (NoClassDefFoundError ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Не обнаружен компонент: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            btnWord.addActionListener(e -> {
                try {
                    WordSaveListener wordSaveListener = new WordSaveListener(this, averageSalary,
                            payrollDetails, payrollMonths, salary, sickMonthDays);
                    wordSaveListener.actionPerformed(e);
                } catch (NoClassDefFoundError ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Не обнаружен компонент: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            btnExcel.addActionListener(e -> {
                try {
                    ExcelSaveListener excelSaveListener = new ExcelSaveListener(this, averageSalary,
                            payrollDetails, payrollMonths, salary, sickMonthDays);
                    excelSaveListener.actionPerformed(e);
                } catch (NoClassDefFoundError ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Не обнаружен компонент: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Вы действительно хотите удалить расчёт о выплате?", "Подтверждение удаления",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            db.deletePayrollData(payrollId);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}