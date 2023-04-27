package by.popolamov.cursework.gui.dialogs;

import by.popolamov.cursework.connect.DBManager;
import by.popolamov.cursework.exceptions.InvalidDateRangeException;
import by.popolamov.cursework.exceptions.InvalidInputException;
import by.popolamov.cursework.model.*;
import by.popolamov.cursework.utils.DateUtils;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static by.popolamov.cursework.exceptions.InvalidInputException.isSalaryValid;
import static by.popolamov.cursework.utils.Calculations.*;
import static by.popolamov.cursework.utils.DateUtils.getDaysBetweenDates;

/**
 * Класс для создания новой выплаты
 *
 * @author Denis Popolamov
 */
public class NewWorkerDialog extends JDialog {
    List<JLabel> lblListRemainingCalendarDays = new ArrayList<>(); // Остаток календарных дней
    List<JLabel> lblListAverageSalary = new ArrayList<>(); // Средняя заработная плата
    List<JTextField> txtListSickDays = new ArrayList<>(); // Больничные, отпускные дни
    List<JTextField> txtListSumOfActualSalary = new ArrayList<>(); //Сумма фактического заработка
    AverageSalary averageSalary = new AverageSalary();
    PayrollDetails payrollDetails = new PayrollDetails();
    PayrollMonths payrollMonths = new PayrollMonths();
    Salary salary = new Salary();
    SickMonthDays sickMonthDays = new SickMonthDays();
    DBManager db = new DBManager();

    public NewWorkerDialog(JFrame parent) {
        super(parent, "Добавить работника", true);
        Dimension dimension = new Dimension(580, 280);
        setPreferredSize(dimension);
        setResizable(false);
        setLocationRelativeTo(parent);

        // Текстовая панель с полями ФИО
        JPanel pnlTop = new JPanel(new GridLayout(1, 2, 30, 10));
        pnlTop.setBorder(BorderFactory.createEmptyBorder(20, 20, -10, 10));
        JLabel lblSurname = new JLabel("Фамилия:");
        lblSurname.setForeground(new Color(255, 255, 255));
        JTextField txtLastName = new JTextField();
        JLabel lblName = new JLabel("Имя:");
        lblName.setForeground(new Color(255, 255, 255));
        JTextField txtName = new JTextField();
        JLabel lblPatronymic = new JLabel("Отчество:");
        lblPatronymic.setForeground(new Color(255, 255, 255));
        JTextField txtPatronymic = new JTextField();

        // Создание компонентов выбора даты
        JXDatePicker dtpFirst = new JXDatePicker();
        JXDatePicker dtpSecond = new JXDatePicker();

        // Установка формата даты для компонента
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dtpFirst.setFormats(dateFormat);
        dtpSecond.setFormats(dateFormat);

        // Установка подсказок для компонентов
        dtpFirst.setToolTipText("Выберите начальную дату");
        dtpSecond.setToolTipText("Выберите конечную дату");

        // Левая панель для заполнения ФИО
        JPanel pnlLeftTop = new JPanel(new GridLayout(4, 4, 10, 10));
        pnlLeftTop.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        pnlLeftTop.setBackground(new Color(27, 161, 226));
        pnlLeftTop.add(lblSurname);
        pnlLeftTop.add(txtLastName);
        pnlLeftTop.add(lblName);
        pnlLeftTop.add(txtName);
        pnlLeftTop.add(lblPatronymic);
        pnlLeftTop.add(txtPatronymic);
        pnlTop.setBackground(new Color(27, 161, 226));
        pnlTop.add(pnlLeftTop, BorderLayout.WEST);

        // Правая панель для выбора даты
        JPanel pnlRightTop = new JPanel(new GridLayout(2, 1, 0, 0));
        pnlRightTop.setBackground(new Color(27, 161, 226));
        pnlRightTop.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));

        // Отдельная панель для выбора даты
        JPanel pnlRightTopDates = new JPanel(new GridLayout(3, 2, 0, 0));
        JLabel lblPeriodOfIllness = new JLabel("Период болезни");
        lblPeriodOfIllness.setForeground(new Color(255, 255, 255));
        pnlRightTop.add(lblPeriodOfIllness, BorderLayout.CENTER);
        JLabel lblFirstDate = new JLabel("Начальная дата:");
        lblFirstDate.setForeground(new Color(255, 255, 255));
        pnlRightTopDates.add(lblFirstDate);
        pnlRightTopDates.setForeground(new Color(255, 255, 255));
        pnlRightTopDates.add(dtpFirst);
        JLabel lblSecondDate = new JLabel("Конечная дата:");
        lblSecondDate.setForeground(new Color(255, 255, 255));
        pnlRightTopDates.add(lblSecondDate);
        pnlRightTopDates.setForeground(new Color(255, 255, 255));
        pnlRightTopDates.add(dtpSecond);
        pnlRightTopDates.setBackground(new Color(27, 161, 226));
        ImageIcon confirmButtonIcon = new ImageIcon("src/main/resources/images/confirm-button-icon.png");
        Image image = confirmButtonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledConfirmButtonIcon = new ImageIcon(image); // создание нового ImageIcon с измененным размером

        pnlRightTop.add(pnlRightTopDates);

        pnlTop.add(pnlRightTop, BorderLayout.EAST);

        JPanel pnlCenter = new JPanel(new BorderLayout());

        // создаем центральную панели с описанием
        JPanel pnlTittleOfCenter = new JPanel(new GridLayout(1, 6, 10, 10));
        pnlTittleOfCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 80, 10));

        pnlTittleOfCenter.add(new JLabel("<html>Месяцы, взятые <br> для исчисления <br> помощи</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Количество <br> календарных <br> дней</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Больничные,<br> отпускные (дней)</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Сумма <br> фактического <br> заработка (руб.)</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Остаток <br> календарных <br> дней</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Средний дневной <br> фактический <br> заработок (руб.)</html>"));
        pnlCenter.add(pnlTittleOfCenter, BorderLayout.NORTH);

        // Ценральная панель с таблицей для заполнения информации об отпускных
        JPanel pnlCenterTableOfCenter = new JPanel(new GridLayout(6, 6, 10, 10));
        pnlCenterTableOfCenter.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Панель с итогами расчётов
        JPanel pnlButtomCenterTable = new JPanel(new GridLayout(1, 6, 10, 10));
        pnlButtomCenterTable.setBorder(BorderFactory.createEmptyBorder(10, 30, 40, 20));

        JLabel[] lblButtomTotal = new JLabel[6];
        Font buttomTotalFont = new Font("Arial", Font.BOLD, 14); // Создание жирного шрифта размером 16
        for (int i = 0; i < lblButtomTotal.length; i++) {
            lblButtomTotal[i] = new JLabel("");
            lblButtomTotal[i].setFont(buttomTotalFont);
            pnlButtomCenterTable.add(lblButtomTotal[i]);
        }
        pnlCenter.add(pnlButtomCenterTable, BorderLayout.SOUTH);

        // Панель с выводом общей информации
        JPanel pnlTotal = new JPanel(new GridLayout(2, 3, 10, 10));
        pnlTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 300));

        JLabel[] lblTotalPanel = new JLabel[6];
        for (int i = 0; i < lblTotalPanel.length; i++) {
            lblTotalPanel[i] = new JLabel("");
            pnlTotal.add(lblTotalPanel[i]);
        }

        // Нижняя панель с кнопками
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.add(pnlTotal, BorderLayout.NORTH);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // добавляем кнопки
        JButton btnCalculation = new JButton("Расчёт");
        btnCalculation.setBackground(new Color(27, 161, 226));
        btnCalculation.setForeground(new Color(255, 255, 255));

        JButton btnConfirm = new JButton(scaledConfirmButtonIcon);
        btnConfirm.setText("Подтвердить");
        btnConfirm.setBackground(new Color(27, 161, 226));
        btnConfirm.setForeground(new Color(255, 255, 255));
        JLabel lblTotalMoney = new JLabel("");
        JButton btnSave = new JButton("Сохранить");
        btnSave.setBackground(new Color(27, 161, 226));
        btnSave.setForeground(new Color(255, 255, 255));
        JButton btnCancel = new JButton("Отмена");
        btnCancel.setBackground(new Color(226, 27, 34));
        btnCancel.setForeground(new Color(255, 255, 255));
        btnCalculation.setVisible(false);
        btnSave.setVisible(false);
        pnlBottom.add(btnConfirm, BorderLayout.WEST);

        // создаем панель для кнопок справа
        JPanel pnlRightButton = new JPanel(new GridLayout(1, 2, 20, 10));
        pnlRightButton.add(btnSave);
        pnlRightButton.add(btnCancel);
        pnlBottom.add(pnlRightButton, BorderLayout.EAST);

        // добавляем все панели на основную панель окна
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlTop, BorderLayout.NORTH);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        // Слушатель для подтверждения ФИО и даты
        btnConfirm.addActionListener(e -> {
            try {
                if (dtpFirst.getDate() == null || dtpSecond.getDate() == null) {
                    throw new InvalidDateRangeException("Выберите корректные даты!");
                }
                if (dtpFirst.getDate().after(dtpSecond.getDate())) {
                    throw new InvalidDateRangeException("Начальная дата должна быть меньше конечной даты!");
                }
                if (txtLastName.getText().isEmpty() || txtName.getText().isEmpty() || txtPatronymic.getText().isEmpty()) {
                    throw new NullPointerException("Не все поля заполнены!");
                }
                pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 200));
                btnConfirm.setVisible(false);
                pnlBottom.add(btnCalculation, BorderLayout.WEST);
                btnCalculation.setVisible(true);
                setSize(750, 700);
                setLocationRelativeTo(parent);

                LocalDate currentDate = dtpFirst.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                payrollMonths.setMonth(DateUtils.getPastSixMonths(currentDate));
                sickMonthDays.setMonthDays(DateUtils.getDaysInPastSixMonths(currentDate));

                for (int i = 0; i <= 5; i++) {
                    pnlCenterTableOfCenter.add(new JLabel(payrollMonths.getMonth().get(i))); // месяц
                    pnlCenterTableOfCenter.add(new JLabel("" + sickMonthDays.getMonthDays().get(i))); // количество дней
                    JTextField txtSickDays = new JTextField();
                    txtListSickDays.add(txtSickDays);
                    pnlCenterTableOfCenter.add(txtSickDays);

                    JTextField txtSumOfActualSalary = new JTextField();
                    txtListSumOfActualSalary.add(txtSumOfActualSalary);
                    pnlCenterTableOfCenter.add(txtSumOfActualSalary);

                    JLabel lblRemainingDay = new JLabel("");
                    lblListRemainingCalendarDays.add(lblRemainingDay);
                    pnlCenterTableOfCenter.add(lblRemainingDay);
                    JLabel lblAverageSalaryTemp = new JLabel("");
                    lblListAverageSalary.add(lblAverageSalaryTemp);
                    pnlCenterTableOfCenter.add(lblAverageSalaryTemp);
                }
                pnlCenter.add(pnlCenterTableOfCenter, BorderLayout.CENTER);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage());
            } catch (InvalidDateRangeException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Слушатель для расчётов
        btnCalculation.addActionListener(e -> {
            try {
                LocalDate currentDate = dtpFirst.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                payrollDetails.setCurrentMonth(DateUtils.getCurrentMonth(currentDate));

                sickMonthDays.setRemainingCalendarDays(calculateRemainingDays(sickMonthDays.getMonthDays(), txtListSickDays));
                averageSalary.setAverageSalary(calculateAverageSalary(txtListSumOfActualSalary, sickMonthDays.getRemainingCalendarDays()));

                payrollDetails.setTotalMonthDays(DateUtils.getTotalDaysInPastSixMonths(currentDate));
                payrollDetails.setTotalRemainingDays(DateUtils.calculateTotalRemainingDays(sickMonthDays.getRemainingCalendarDays()));
                payrollDetails.setTotalSickDates(totalSickDays(txtListSickDays));
                payrollDetails.setTotalSalary(calculateTotalSalary(txtListSumOfActualSalary));
                payrollDetails.setTotalAverageSalary(calculateTotalAverageSalary(payrollDetails.getTotalSalary(), payrollDetails.getTotalRemainingDays()));
                isSalaryValid(txtListSickDays, sickMonthDays.getMonthDays());

                pnlButtomCenterTable.setBorder(BorderFactory.createEmptyBorder(10, 30, 60, 20));
                for (int i = 0; i < sickMonthDays.getRemainingCalendarDays().size(); i++) {
                    lblListRemainingCalendarDays.get(i).setText("" + sickMonthDays.getRemainingCalendarDays().get(i));
                    lblListAverageSalary.get(i).setText("" + averageSalary.getAverageSalary().get(i));
                }

                payrollDetails.setIllnessDays(getDaysBetweenDates(dtpFirst, dtpSecond));
                payrollDetails.setEightyPercentSalary(calculate80PercentOfAverageSalary(payrollDetails.getTotalAverageSalary(), payrollDetails.getIllnessDays()));
                payrollDetails.setHundredPercentSalary(calculateFullSalary(payrollDetails.getTotalAverageSalary(), payrollDetails.getIllnessDays()));

                lblButtomTotal[0].setText("Итого:");
                lblButtomTotal[1].setText("" + payrollDetails.getTotalMonthDays());
                lblButtomTotal[2].setText("" + payrollDetails.getTotalSickDates());
                lblButtomTotal[3].setText(payrollDetails.getTotalSalary() + " р.б.");
                lblButtomTotal[4].setText("" + payrollDetails.getTotalRemainingDays());
                lblButtomTotal[5].setText(payrollDetails.getTotalAverageSalary() + " р.б.");

                lblTotalPanel[0].setText("<html>Месяц<br> нетрудоспособности</html>");
                lblTotalPanel[1].setText("<html>За дни в размере<br> 80% заработка</html>");
                lblTotalPanel[2].setText("<html>За дни в размере<br> 100% заработка</html>");
                lblTotalPanel[3].setText(payrollDetails.getCurrentMonth());
                lblTotalPanel[4].setText(payrollDetails.getEightyPercentSalary() + " р.б.");
                lblTotalPanel[5].setText(payrollDetails.getHundredPercentSalary() + " р.б.");

                pnlTittleOfCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                pnlTotal.setVisible(true);

                double totalPayrollSum = payrollDetails.getEightyPercentSalary() + payrollDetails.getHundredPercentSalary();
                String formattedTotalPayrollSum = String.format(Locale.US,"%.2f", totalPayrollSum);
                payrollDetails.setTotalPayrollSum(Double.parseDouble(formattedTotalPayrollSum));

                lblTotalMoney.setText("Всего: " + payrollDetails.getTotalPayrollSum() + " р.б.");
                pnlBottom.add(lblTotalMoney);
                btnSave.setVisible(true);

            } catch (InvalidInputException ex) {
                JOptionPane.showMessageDialog(null, "Введённое вами число дней, превышает количество дней в месяце.");
            }
        });

        // Слушатель для сохранения расчётов
        btnSave.addActionListener(e -> {
            try {
                salary.setSalaryTextField(txtListSumOfActualSalary);
                sickMonthDays.setSickMonthDaysTextField(txtListSickDays);
                payrollDetails.setUserName(txtName.getText());
                payrollDetails.setUserSurName(txtLastName.getText());
                payrollDetails.setUserPatronymic(txtPatronymic.getText());

                Date firstUtilDate = dtpFirst.getDate();
                String firstDateString = new SimpleDateFormat("yyyy-MM-dd").format(firstUtilDate);
                java.sql.Date fistDate = java.sql.Date.valueOf(firstDateString);
                payrollDetails.setStartIllnessDate(fistDate);

                Date secondUtilDate = dtpSecond.getDate();
                String secondDateToString = new SimpleDateFormat("yyyy-MM-dd").format(secondUtilDate);
                java.sql.Date secondDate = java.sql.Date.valueOf(secondDateToString);
                payrollDetails.setEndIllnessDate(secondDate);

                db.addPayrollDetails(payrollDetails);
                db.saveAverageSalary(averageSalary);
                db.savePayrollMonths(payrollMonths);
                db.savePayrollSalary(salary);
                db.savePayrollSickMonthDays(sickMonthDays);
                parent.repaint();
                dispose();
            } catch (Exception ex) {
                System.out.println("Что то пошло не так!");
            }
        });

        // Слушатель для закрытия окна
        btnCancel.addActionListener(e -> {
            dispose();
        });

        add(pnlMain);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
