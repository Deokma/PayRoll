package by.popolamov.cursework.gui;

/**
 * Класс для создания н
 *
 * @author Denis Popolamov
 */

import by.popolamov.cursework.calculate.DateUtils;
import by.popolamov.cursework.connect.DBManager;
import by.popolamov.cursework.exceptions.InvalidDateRangeException;
import by.popolamov.cursework.exceptions.InvalidInputException;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.popolamov.cursework.calculate.Calculations.*;
import static by.popolamov.cursework.calculate.DateUtils.getDaysBetweenDates;
import static by.popolamov.cursework.exceptions.InvalidInputException.isSalaryValid;


public class NewWorker extends JDialog {
    private List<String> pastMonths; // список 6 предыдущих месяцев
    private List<Integer> daysInMonths; // список дней в 6 предыдущих месяцах
    private int totalDates; // общее количество дней в предыдущих месяцах
    private int totalRemainingCalendarDays; // общее количество дней, с учётом прогулов
    private int totalSickDays; // общее количество дней прогулов
    private double totalSalary; // общая заработная плата
    private double totalAverageSalary; // общая средняя заработная плата
    private double totalSum; //общая сумма
    List<JLabel> remainingCalendarDaysLabel = new ArrayList<>();
    List<JLabel> avarageSalaryLabel = new ArrayList<>();

    public NewWorker(JFrame parent) {
        super(parent, "Добавить работника", true);
        Dimension dimension = new Dimension(580, 280);
        setPreferredSize(dimension);
        setResizable(false);
        setLocationRelativeTo(parent);

        // Текстовая панель с полями ФИО
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 30, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        JLabel surnameLabel = new JLabel("Фамилия:");
        surnameLabel.setForeground(new Color(255, 255, 255));
        JTextField surnameTextField = new JTextField();
        JLabel nameLabel = new JLabel("Имя:");
        nameLabel.setForeground(new Color(255, 255, 255));
        JTextField nameTextField = new JTextField();
        JLabel patronymicLabel = new JLabel("Отчество:");
        patronymicLabel.setForeground(new Color(255, 255, 255));
        JTextField patronymicTextField = new JTextField();

        // Создание компанентов выбора даты
        JXDatePicker firstDatePicker = new JXDatePicker();
        JXDatePicker secondDatePicker = new JXDatePicker();

        // Установка формата даты для компонента
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        firstDatePicker.setFormats(dateFormat);
        secondDatePicker.setFormats(dateFormat);

        // Установка подсказок для компонентов
        firstDatePicker.setToolTipText("Выберите начальную дату");
        secondDatePicker.setToolTipText("Выберите конечную дату");

        // Левая панель для заполнения ФИО
        JPanel leftTopPanet = new JPanel(new GridLayout(4, 4, 10, 10));
        leftTopPanet.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        leftTopPanet.setBackground(new Color(27, 161, 226));
        leftTopPanet.add(surnameLabel);
        leftTopPanet.add(surnameTextField);
        leftTopPanet.add(nameLabel);
        leftTopPanet.add(nameTextField);
        leftTopPanet.add(patronymicLabel);
        leftTopPanet.add(patronymicTextField);
        topPanel.setBackground(new Color(27, 161, 226));
        topPanel.add(leftTopPanet, BorderLayout.WEST);

        // Правая панель для выбора даты
        JPanel rightTopPanet = new JPanel(new GridLayout(2, 1, 0, 0));
        rightTopPanet.setBackground(new Color(27, 161, 226));
        rightTopPanet.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // Отдельная панель для выбора даты
        JPanel rightTopPanetDates = new JPanel(new GridLayout(3, 2, 0, 0));
        JLabel periodOfillnessLabel = new JLabel("Период болезни");
        periodOfillnessLabel.setForeground(new Color(255, 255, 255));
        rightTopPanet.add(periodOfillnessLabel, BorderLayout.CENTER);
        JLabel startDateLabel = new JLabel("Начальная дата:");
        startDateLabel.setForeground(new Color(255, 255, 255));
        rightTopPanetDates.add(startDateLabel);
        rightTopPanetDates.setForeground(new Color(255, 255, 255));
        rightTopPanetDates.add(firstDatePicker);
        JLabel endDateLabel = new JLabel("Конечная дата:");
        endDateLabel.setForeground(new Color(255, 255, 255));
        rightTopPanetDates.add(endDateLabel);
        rightTopPanetDates.setForeground(new Color(255, 255, 255));
        rightTopPanetDates.add(secondDatePicker);
        rightTopPanetDates.setBackground(new Color(27, 161, 226));
        ImageIcon confirmButtonIcon = new ImageIcon("src/main/resources/images/confirm-button-icon.png");
        Image image = confirmButtonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledConfirmButtonIcon = new ImageIcon(image); // создание нового ImageIcon с измененным размером

        rightTopPanet.add(rightTopPanetDates);

        topPanel.add(rightTopPanet, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());

        // создаем центральную панели с описание
        JPanel tittleOfCenterPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        tittleOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 80, 10));

        tittleOfCenterPanel.add(new JLabel("<html>Месяцы, взятые <br> для исчисления <br> помощи</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Количество <br> календарных <br> дней</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Командировочные <br> больничные,<br> отпускные (дней)</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Сумма <br> фактического <br> заработка (руб.)</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Остаток <br> календарных <br> дней</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Средний дневной <br> фактический <br> заработок (руб.)</html>"));
        centerPanel.add(tittleOfCenterPanel, BorderLayout.NORTH);
        //centerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Ценральная панель с таблицей для заполнения информации о отпускных
        JPanel centerTableOfCenterPanel = new JPanel(new GridLayout(6, 6, 10, 10));
        centerTableOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Компоненты для расчёта в центральной таблице
        List<JTextField> sickDaysTextField = new ArrayList<>(); // Больничные, отпускные дни
        List<JTextField> sumOfActualSalaryTextField = new ArrayList<>(); //Сумма фактического заработка
        //JTextField[] sumOfActualSalaryTextField = new JTextField[6]; //Сумма фактического заработка


        // Панель с итогами расчётов
        JPanel buttomTableOfCentalPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        buttomTableOfCentalPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 40, 20));

        JLabel[] buttomTotalLabel = new JLabel[6];
        Font buttomTotalFont = new Font("Arial", Font.BOLD, 14); // Создание жирного шрифта размером 16
        for (int i = 0; i < buttomTotalLabel.length; i++) {
            buttomTotalLabel[i] = new JLabel("");
            buttomTotalLabel[i].setFont(buttomTotalFont);
            buttomTableOfCentalPanel.add(buttomTotalLabel[i]);
        }
        centerPanel.add(buttomTableOfCentalPanel, BorderLayout.SOUTH);

        // Панель с выводом общей информации
        JPanel totalPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 300));

        JLabel[] totalPanelLabel = new JLabel[6];
        for (int i = 0; i < totalPanelLabel.length; i++) {
            totalPanelLabel[i] = new JLabel("");
            totalPanel.add(totalPanelLabel[i]);
        }

        // Нижняя панель с кнопками
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // добавляем кнопки
        JButton calculationButton = new JButton("Расчёт");
        calculationButton.setBackground(new Color(27, 161, 226));
        calculationButton.setForeground(new Color(255, 255, 255));

        JButton confirmButton = new JButton(scaledConfirmButtonIcon);
        confirmButton.setText("Check");
        confirmButton.setBackground(new Color(27, 161, 226));
        confirmButton.setForeground(new Color(255, 255, 255));
        JLabel totalMoneyLabel = new JLabel("");
        JButton saveButton = new JButton("Сохранить");
        saveButton.setBackground(new Color(27, 161, 226));
        saveButton.setForeground(new Color(255, 255, 255));
        JButton cancelButton = new JButton("Отмена");
        cancelButton.setBackground(new Color(226, 27, 34));
        cancelButton.setForeground(new Color(255, 255, 255));
        calculationButton.setVisible(false);
        saveButton.setVisible(false);
        bottomPanel.add(confirmButton, BorderLayout.WEST);

        // создаем панель для кнопок справа
        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        rightButtonPanel.add(saveButton);
        rightButtonPanel.add(cancelButton);
        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);

        // добавляем все панели на основную панель окна
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Слушатель для подтверждения ФИО и даты
        confirmButton.addActionListener(e -> {
            try {
                if (firstDatePicker.getDate() == null || secondDatePicker.getDate() == null) {
                    throw new InvalidDateRangeException("Выберите корректные даты!");
                }
                if (firstDatePicker.getDate().after(secondDatePicker.getDate())) {
                    throw new InvalidDateRangeException("Начальная дата должна быть меньше конечной даты!");
                }
                if (surnameTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || patronymicTextField.getText().isEmpty()) {
                    throw new NullPointerException("Не все поля заполнены!");
                }
                topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 200));
                confirmButton.setVisible(false);
                bottomPanel.add(calculationButton, BorderLayout.WEST);
                calculationButton.setVisible(true);
                setSize(750, 700);
                setLocationRelativeTo(parent);

                LocalDate localDate = firstDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                pastMonths = DateUtils.getPastSixMonths(localDate);
                daysInMonths = DateUtils.getDaysInPastSixMonths(localDate);

                for (int i = 0; i <= 5; i++) {
                    centerTableOfCenterPanel.add(new JLabel(pastMonths.get(i))); // месяц
                    centerTableOfCenterPanel.add(new JLabel("" + daysInMonths.get(i))); // количество дней
                    JTextField sickDaysTextFieldTemp = new JTextField();
                    sickDaysTextFieldTemp.setName("sickDaysTextField" + i);
                    sickDaysTextField.add(sickDaysTextFieldTemp);
                    centerTableOfCenterPanel.add(sickDaysTextFieldTemp);

                    JTextField sumOfActualSalaryTextFieldTemp = new JTextField();
                    sumOfActualSalaryTextFieldTemp.setName("sickDaysTextField" + i);
                    sumOfActualSalaryTextField.add(sumOfActualSalaryTextFieldTemp);
                    centerTableOfCenterPanel.add(sumOfActualSalaryTextFieldTemp);

                    JLabel remainingDayLabel = new JLabel("");
                    remainingCalendarDaysLabel.add(remainingDayLabel);
                    centerTableOfCenterPanel.add(remainingDayLabel);
                    JLabel avarageSalaryLabelTemp = new JLabel("");
                    avarageSalaryLabel.add(avarageSalaryLabelTemp);
                    centerTableOfCenterPanel.add(avarageSalaryLabelTemp);
                }
                centerPanel.add(centerTableOfCenterPanel, BorderLayout.CENTER);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage());
            } catch (InvalidDateRangeException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Слушатель для расчётов
        calculationButton.addActionListener(e -> {
            try {
                LocalDate localDate = firstDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                String currentMonth = DateUtils.getCurrentMonth(localDate);

                List<Integer> remainingCalendarDays = calculateRemainingDays(daysInMonths, sickDaysTextField);
                List<Double> remainingAvarageSalary = calculateAverageSalary(sumOfActualSalaryTextField, remainingCalendarDays);

                daysInMonths = DateUtils.getDaysInPastSixMonths(localDate);
                totalDates = DateUtils.getTotalDaysInPastSixMonths(localDate);
                totalRemainingCalendarDays = DateUtils.calculateTotalRemainingDays(remainingCalendarDays);
                totalSickDays = totalSickDays(sickDaysTextField);
                totalSalary = calculateTotalSalary(sumOfActualSalaryTextField);
                totalAverageSalary = calculateTotalAverageSalary(remainingAvarageSalary);
                isSalaryValid(sickDaysTextField, daysInMonths);

                buttomTableOfCentalPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 60, 20));
                for (int i = 0; i < remainingCalendarDays.size(); i++) {
                    remainingCalendarDaysLabel.get(i).setText("" + remainingCalendarDays.get(i));
                    avarageSalaryLabel.get(i).setText("" + remainingAvarageSalary.get(i));
                }

                int numberOfDaysOfIllness = getDaysBetweenDates(firstDatePicker, secondDatePicker);
                double calculated80PercentOfAverageSalary = calculate80PercentOfAverageSalary(totalAverageSalary, numberOfDaysOfIllness);
                double calculatedFullSalary = calculateFullSalary(totalAverageSalary, numberOfDaysOfIllness);


                buttomTotalLabel[0].setText("Итого:");
                buttomTotalLabel[1].setText("" + totalDates);
                buttomTotalLabel[2].setText("" + totalSickDays);
                buttomTotalLabel[3].setText(totalSalary + " р.б.");
                buttomTotalLabel[4].setText("" + totalRemainingCalendarDays);
                buttomTotalLabel[5].setText(totalAverageSalary + " р.б.");

                totalPanelLabel[0].setText("<html>Месяц<br> нетрудоспособности</html>");
                totalPanelLabel[1].setText("<html>За дни в размере<br> 80% заработка</html>");
                totalPanelLabel[2].setText("<html>За дни в размере<br> 100% заработка</html>");
                totalPanelLabel[3].setText(currentMonth);
                totalPanelLabel[4].setText(calculated80PercentOfAverageSalary + " р.б.");
                totalPanelLabel[5].setText(calculatedFullSalary + " р.б.");

                tittleOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                totalPanel.setVisible(true);
                totalSum = ((calculated80PercentOfAverageSalary + calculatedFullSalary) * 100.0) / 100.0;
                totalMoneyLabel.setText("Всего: " + totalSum + " р.б.");
                bottomPanel.add(totalMoneyLabel);
                saveButton.setVisible(true);
            } catch (InvalidInputException ex) {
                JOptionPane.showMessageDialog(null, "Введённое вами число дней, превышает количество дней в месяце.");
            }
        });

        // Слушатель для сохранения расчётов
        saveButton.addActionListener(e -> {
           // new SaveWindow(this);
            try {
                String userSurname = surnameTextField.getText();
                String userName = nameTextField.getText();
                String userPatrinimic = patronymicTextField.getText();

                Date firstUtilDate = firstDatePicker.getDate();
                String firstDateString = new SimpleDateFormat("yyyy-MM-dd").format(firstUtilDate);
                java.sql.Date fistSqlDate = java.sql.Date.valueOf(firstDateString);

                Date secondUtilDate = secondDatePicker.getDate();
                String secondDateString = new SimpleDateFormat("yyyy-MM-dd").format(secondUtilDate);
                java.sql.Date secondSqlDate = java.sql.Date.valueOf(secondDateString);
                DBManager db = new DBManager();

                int payrollId = db.addEmployee(userSurname, userName, userPatrinimic, fistSqlDate, secondSqlDate,totalSum);

                db.addPayrollDetails(payrollId,totalSickDays,totalSalary,totalRemainingCalendarDays,totalAverageSalary,totalSum);

                dispose();
            } catch (Exception ex) {
                System.out.println("Что то пошло не так!");
            }
        });

        // Слушатель для закрытия окна
        cancelButton.addActionListener(e -> {
            dispose();
        });

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
