package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */

import by.popolamov.cursework.calculate.DateUtils;
import by.popolamov.cursework.exceptions.InvalidDateRangeException;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


public class NewWorker extends JDialog {

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
        JXDatePicker startDatePicker = new JXDatePicker();
        JXDatePicker endDatePicker = new JXDatePicker();

        // Установка формата даты для компонента
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        startDatePicker.setFormats(dateFormat);
        endDatePicker.setFormats(dateFormat);

        // Установка подсказок для компонентов
        startDatePicker.setToolTipText("Выберите начальную дату");
        endDatePicker.setToolTipText("Выберите конечную дату");

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
        rightTopPanetDates.add(startDatePicker);
        JLabel endDateLabel = new JLabel("Конечная дата:");
        endDateLabel.setForeground(new Color(255, 255, 255));
        rightTopPanetDates.add(endDateLabel);
        rightTopPanetDates.setForeground(new Color(255, 255, 255));
        rightTopPanetDates.add(endDatePicker);
        rightTopPanetDates.setBackground(new Color(27, 161, 226));
        ImageIcon confirmButtonIcon = new ImageIcon(getClass().getResource("/images/confirm-button-icon.png"));
        Image image = confirmButtonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledConfirmButtonIcon = new ImageIcon(image); // создание нового ImageIcon с измененным размером

        rightTopPanet.add(rightTopPanetDates);

        topPanel.add(rightTopPanet, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());

        // создаем центральную панели с описание
        JPanel tittleOfCenterPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        tittleOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 70, 10));

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

        JTextField[] sickDaysTextField = new JTextField[6]; // больничные отпускные дни
        JTextField[] sumOfActualSalaryTextField = new JTextField[6]; //Сумма фактического заработка

        // Панель с итогами расчётов
        JPanel buttomTableOfCentalPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        buttomTableOfCentalPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 80, 20));
        buttomTableOfCentalPanel.add(new JLabel("Итого:"));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        centerPanel.add(buttomTableOfCentalPanel, BorderLayout.SOUTH);

        // Панель с выводом общей информации
        JPanel totalPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 300));

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
        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
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
                if (startDatePicker.getDate() == null || endDatePicker.getDate() == null) {
                    throw new InvalidDateRangeException("Выберите корректные даты!");
                }
                if (startDatePicker.getDate().after(endDatePicker.getDate())) {
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

                LocalDate localDate = startDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                List<String> pastMonths = DateUtils.getPastSixMonths(localDate);
                List<Integer> daysInMonths = DateUtils.getDaysInPastSixMonths(localDate);

                for (int i = 0; i <= 5; i++) {
                    centerTableOfCenterPanel.add(new JLabel(pastMonths.get(i))); // месяц
                    centerTableOfCenterPanel.add(new JLabel("" + daysInMonths.get(i))); // количество дней
                    sickDaysTextField[i] = new JTextField();
                    sickDaysTextField[i].setName("sickDaysTextField" + (i));
                    centerTableOfCenterPanel.add(sickDaysTextField[i]);
                    sumOfActualSalaryTextField[i] = new JTextField();
                    sumOfActualSalaryTextField[i].setName("sumOfActualSalaryTextField" + (i));
                    centerTableOfCenterPanel.add(sumOfActualSalaryTextField[i]);
                    centerTableOfCenterPanel.add(new JLabel("осткд" + i));
                    centerTableOfCenterPanel.add(new JLabel("сфз" + i));
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
            LocalDate localDate = startDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            tittleOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            String currentMonth = DateUtils.getCurrentMonth(localDate);
            totalPanel.add(new JLabel("<html>Месяц<br> нетрудоспособности</html>"));
            totalPanel.add(new JLabel("<html>За дни в размере<br> 80% заработка</html>"));
            totalPanel.add(new JLabel("<html>За дни в размере<br> 100% заработка</html>"));
            totalPanel.add(new JLabel(currentMonth));

            totalPanel.add(new JLabel("11,11 р.б."));
            totalPanel.add(new JLabel("100 р.б."));

            JLabel totalMoneyLabel = new JLabel("Всего: 111,11 р.б.");
            bottomPanel.add(totalMoneyLabel);
            saveButton.setVisible(true);
        });

        // Слушатель для сохранения расчётов
        saveButton.addActionListener(e -> {
            //new SaveWindow(this);
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
