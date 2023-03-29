package by.popolamov.cursework.gui;

/**
 * Класс для создания н
 *
 * @author Denis Popolamov
 */

import by.popolamov.cursework.connect.DBManager;
import by.popolamov.cursework.exceptions.InvalidDateRangeException;
import by.popolamov.cursework.exceptions.InvalidInputException;
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

import static by.popolamov.cursework.exceptions.InvalidInputException.isSalaryValid;
import static by.popolamov.cursework.utils.Calculations.*;
import static by.popolamov.cursework.utils.DateUtils.getDaysBetweenDates;


public class NewWorker extends JDialog {
    private List<String> pastMonths; // список 6 предыдущих месяцев
    private List<Integer> daysInMonths; // список дней в 6 предыдущих месяцах
    private int totalDates; // общее количество дней в предыдущих месяцах
    private int totalRemainingCalendarDays; // общее количество дней, с учётом прогулов
    private int totalSickDays; // общее количество дней прогулов
    private double totalSalary; // общая заработная плата
    private double totalAverageSalary; // общая средняя заработная плата
    private double totalSum; //общая сумма
    List<JLabel> lblListRemainingCalendarDays = new ArrayList<>(); // Остаток календарных дней
    List<JLabel> lblListAverageSalary = new ArrayList<>(); // Средняя заработная плата

    String currentMonthSql;
    Double eightyPecentSalarySql;
    Double hundredPecentSalarySql;
    List<Double> sqlAvarageSalary;
    private MainWindow mainWindow;

    public NewWorker(JFrame parent) {
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
        JTextField txtSurname = new JTextField();
        JLabel lblName = new JLabel("Имя:");
        lblName.setForeground(new Color(255, 255, 255));
        JTextField txtName = new JTextField();
        JLabel lblPatronymic = new JLabel("Отчество:");
        lblPatronymic.setForeground(new Color(255, 255, 255));
        JTextField txtPatronymic = new JTextField();

        // Создание компанентов выбора даты
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
        pnlLeftTop.add(txtSurname);
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

        // создаем центральную панели с описание
        JPanel pnlTittleOfCenter = new JPanel(new GridLayout(1, 6, 10, 10));
        pnlTittleOfCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 80, 10));

        pnlTittleOfCenter.add(new JLabel("<html>Месяцы, взятые <br> для исчисления <br> помощи</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Количество <br> календарных <br> дней</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Командировочные <br> больничные,<br> отпускные (дней)</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Сумма <br> фактического <br> заработка (руб.)</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Остаток <br> календарных <br> дней</html>"));
        pnlTittleOfCenter.add(new JLabel("<html>Средний дневной <br> фактический <br> заработок (руб.)</html>"));
        pnlCenter.add(pnlTittleOfCenter, BorderLayout.NORTH);
        //pnlCenter.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Ценральная панель с таблицей для заполнения информации о отпускных
        JPanel pnlCenterTableOfCenter = new JPanel(new GridLayout(6, 6, 10, 10));
        pnlCenterTableOfCenter.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Компоненты для расчёта в центральной таблице
        List<JTextField> txtSickDays = new ArrayList<>(); // Больничные, отпускные дни
        List<JTextField> txtSumOfActualSalary = new ArrayList<>(); //Сумма фактического заработка
        //JTextField[] txtSumOfActualSalary = new JTextField[6]; //Сумма фактического заработка


        // Панель с итогами расчётов
        JPanel pnlButtomTableOfCental = new JPanel(new GridLayout(1, 6, 10, 10));
        pnlButtomTableOfCental.setBorder(BorderFactory.createEmptyBorder(10, 30, 40, 20));

        JLabel[] lblButtomTotal = new JLabel[6];
        Font buttomTotalFont = new Font("Arial", Font.BOLD, 14); // Создание жирного шрифта размером 16
        for (int i = 0; i < lblButtomTotal.length; i++) {
            lblButtomTotal[i] = new JLabel("");
            lblButtomTotal[i].setFont(buttomTotalFont);
            pnlButtomTableOfCental.add(lblButtomTotal[i]);
        }
        pnlCenter.add(pnlButtomTableOfCental, BorderLayout.SOUTH);

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
        btnConfirm.setText("Check");
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
                if (txtSurname.getText().isEmpty() || txtName.getText().isEmpty() || txtPatronymic.getText().isEmpty()) {
                    throw new NullPointerException("Не все поля заполнены!");
                }
                pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 200));
                btnConfirm.setVisible(false);
                pnlBottom.add(btnCalculation, BorderLayout.WEST);
                btnCalculation.setVisible(true);
                setSize(750, 700);
                setLocationRelativeTo(parent);

                LocalDate localDate = dtpFirst.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                pastMonths = DateUtils.getPastSixMonths(localDate);
                daysInMonths = DateUtils.getDaysInPastSixMonths(localDate);

                for (int i = 0; i <= 5; i++) {
                    pnlCenterTableOfCenter.add(new JLabel(pastMonths.get(i))); // месяц
                    pnlCenterTableOfCenter.add(new JLabel("" + daysInMonths.get(i))); // количество дней
                    JTextField txtSickDaysTemp = new JTextField();
                    txtSickDaysTemp.setName("txtSickDays" + i);
                    txtSickDays.add(txtSickDaysTemp);
                    pnlCenterTableOfCenter.add(txtSickDaysTemp);

                    JTextField txtSumOfActualSalaryTemp = new JTextField();
                    txtSumOfActualSalaryTemp.setName("txtSickDays" + i);
                    txtSumOfActualSalary.add(txtSumOfActualSalaryTemp);
                    pnlCenterTableOfCenter.add(txtSumOfActualSalaryTemp);

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
                LocalDate localDate = dtpFirst.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                String currentMonth = DateUtils.getCurrentMonth(localDate);
                currentMonthSql = currentMonth;

                List<Integer> remainingCalendarDays = calculateRemainingDays(daysInMonths, txtSickDays);
                List<Double> remainingAverageSalary = calculateAverageSalary(txtSumOfActualSalary, remainingCalendarDays);

                sqlAvarageSalary = remainingAverageSalary;

                daysInMonths = DateUtils.getDaysInPastSixMonths(localDate);
                totalDates = DateUtils.getTotalDaysInPastSixMonths(localDate);
                totalRemainingCalendarDays = DateUtils.calculateTotalRemainingDays(remainingCalendarDays);
                totalSickDays = totalSickDays(txtSickDays);
                totalSalary = calculateTotalSalary(txtSumOfActualSalary);
                totalAverageSalary = calculateTotalAverageSalary(totalSalary, totalRemainingCalendarDays);
                isSalaryValid(txtSickDays, daysInMonths);

                pnlButtomTableOfCental.setBorder(BorderFactory.createEmptyBorder(10, 30, 60, 20));
                for (int i = 0; i < remainingCalendarDays.size(); i++) {
                    lblListRemainingCalendarDays.get(i).setText("" + remainingCalendarDays.get(i));
                    lblListAverageSalary.get(i).setText("" + remainingAverageSalary.get(i));
                }

                int numberOfDaysOfIllness = getDaysBetweenDates(dtpFirst, dtpSecond);
                double calculated80PercentOfAverageSalary = calculate80PercentOfAverageSalary(totalAverageSalary, numberOfDaysOfIllness);
                double calculatedFullSalary = calculateFullSalary(totalAverageSalary, numberOfDaysOfIllness);
                eightyPecentSalarySql = calculated80PercentOfAverageSalary;
                hundredPecentSalarySql = calculatedFullSalary;

                lblButtomTotal[0].setText("Итого:");
                lblButtomTotal[1].setText("" + totalDates);
                lblButtomTotal[2].setText("" + totalSickDays);
                lblButtomTotal[3].setText(totalSalary + " р.б.");
                lblButtomTotal[4].setText("" + totalRemainingCalendarDays);
                lblButtomTotal[5].setText(totalAverageSalary + " р.б.");

                lblTotalPanel[0].setText("<html>Месяц<br> нетрудоспособности</html>");
                lblTotalPanel[1].setText("<html>За дни в размере<br> 80% заработка</html>");
                lblTotalPanel[2].setText("<html>За дни в размере<br> 100% заработка</html>");
                lblTotalPanel[3].setText(currentMonth);
                lblTotalPanel[4].setText(calculated80PercentOfAverageSalary + " р.б.");
                lblTotalPanel[5].setText(calculatedFullSalary + " р.б.");

                pnlTittleOfCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                pnlTotal.setVisible(true);
                totalSum = ((calculated80PercentOfAverageSalary + calculatedFullSalary) * 100.0) / 100.0;
                lblTotalMoney.setText("Всего: " + totalSum + " р.б.");
                pnlBottom.add(lblTotalMoney);
                btnSave.setVisible(true);
            } catch (InvalidInputException ex) {
                JOptionPane.showMessageDialog(null, "Введённое вами число дней, превышает количество дней в месяце.");
            }
        });

        // Слушатель для сохранения расчётов
        btnSave.addActionListener(e -> {
            try {
                String userSurname = txtSurname.getText();
                String userName = txtName.getText();
                String userPatrinimic = txtPatronymic.getText();

                Date firstUtilDate = dtpFirst.getDate();
                String firstDateString = new SimpleDateFormat("yyyy-MM-dd").format(firstUtilDate);
                java.sql.Date fistSqlDate = java.sql.Date.valueOf(firstDateString);

                Date secondUtilDate = dtpSecond.getDate();
                String secondDateString = new SimpleDateFormat("yyyy-MM-dd").format(secondUtilDate);
                java.sql.Date secondSqlDate = java.sql.Date.valueOf(secondDateString);
                DBManager db = new DBManager();

                //int payrollId = db.addEmployee(userSurname, userName, userPatrinimic, fistSqlDate, secondSqlDate, totalSum);

                db.addPayrollDetails(totalSickDays, totalSalary,
                        totalRemainingCalendarDays, totalAverageSalary, totalSum,
                        userSurname, userName, userPatrinimic, fistSqlDate, secondSqlDate,
                        currentMonthSql, eightyPecentSalarySql, hundredPecentSalarySql);
                db.saveAverageSalary(sqlAvarageSalary);
                db.savePayrollMonths(pastMonths);
                db.savePayrollSalary(txtSumOfActualSalary);
                db.savePayrollWorkDays(daysInMonths, lblListRemainingCalendarDays);
                //lblListAverageSalary среднее зп
                //lblListRemainingCalendarDays остаток календарных дней
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
