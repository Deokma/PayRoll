package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */

import by.popolamov.cursework.calculate.MonthUtils;
import by.popolamov.cursework.exceptions.InvalidDateRangeException;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class NewWorker extends JDialog {

    // Создание компонента JXDatePicker
    JXDatePicker startDatePicker = new JXDatePicker();
    JXDatePicker endDatePicker = new JXDatePicker();

    public NewWorker(JFrame parent) {
        super(parent, "Добавить работника", true);
        Dimension dimension = new Dimension(580,250);
        setPreferredSize(dimension);
        setResizable(false);
        setLocationRelativeTo(parent);


        // создаем верхнюю панель с текстовыми полями
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 30, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 10));
        JLabel surnameLabel = new JLabel("Фамилия:");
        JTextField surnameTextField = new JTextField();
        JLabel nameLabel = new JLabel("Имя:");
        JTextField nameTextField = new JTextField();
        JLabel patronymicLabel = new JLabel("Отчество:");
        JTextField patronymicTextField = new JTextField();


        // Установка формата даты для компонента
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        startDatePicker.setFormats(dateFormat);
        endDatePicker.setFormats(dateFormat);

        // Установка подсказок для компонентов
        startDatePicker.setToolTipText("Выберите начальную дату");
        endDatePicker.setToolTipText("Выберите конечную дату");

        JPanel leftTopPanet = new JPanel(new GridLayout(4, 4, 10, 10));
        leftTopPanet.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 0));
        leftTopPanet.add(surnameLabel);
        leftTopPanet.add(surnameTextField);
        leftTopPanet.add(nameLabel);
        leftTopPanet.add(nameTextField);
        leftTopPanet.add(patronymicLabel);
        leftTopPanet.add(patronymicTextField);

        topPanel.add(leftTopPanet, BorderLayout.WEST);

        JPanel rightTopPanet = new JPanel(new GridLayout(2, 1, 0, 0));
        rightTopPanet.setBorder(BorderFactory.createEmptyBorder(0, 20, 60, 20));
        JPanel rightTopPanetDates = new JPanel(new GridLayout(3, 2, 0, 0));
        JLabel periodOfillnessLabel = new JLabel("Период болезни");
        rightTopPanet.add(periodOfillnessLabel, BorderLayout.CENTER);
        rightTopPanetDates.add(new JLabel("Начальная дата:"));
        rightTopPanetDates.add(startDatePicker);
        rightTopPanetDates.add(new JLabel("Конечная дата:"));
        rightTopPanetDates.add(endDatePicker);
        ImageIcon confirmButtonIcon = new ImageIcon(getClass().getResource("/images/confirm-button-icon.png"));
        Image image = confirmButtonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // масштабирование картинки до 50x50
        ImageIcon scaledConfirmButtonIcon = new ImageIcon(image); // создание нового ImageIcon с измененным размером

        JButton confirmButton = new JButton(scaledConfirmButtonIcon);
        confirmButton.setText("Check");
        confirmButton.setPreferredSize(new Dimension(20, 20));
        leftTopPanet.add(confirmButton);
        rightTopPanet.add(rightTopPanetDates);

        topPanel.add(rightTopPanet, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());

        // создаем центральную панель с сеткой 6х7
        JPanel tittleOfCenterPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        tittleOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tittleOfCenterPanel.add(new JLabel("<html>Месяцы, взятые <br> для исчисления <br> помощи</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Количество <br> календарных <br> дней</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Командировочные <br> больничные,<br> отпускные (дней)</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Остаток <br> календарных <br> дней</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Сумма <br> фактического <br> заработка (руб.)</html>"));
        tittleOfCenterPanel.add(new JLabel("<html>Средний дневной <br> фактический <br> заработок (руб.)</html>"));
        //centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //centerPanel.setBackground(Color.cyan);
        centerPanel.add(tittleOfCenterPanel, BorderLayout.NORTH);
        // centerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        JPanel centerTableOfCenterPanel = new JPanel(new GridLayout(6, 6, 10, 10));
        centerTableOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 80, 20));
        //centerTableOfCenterPanel.setLayout(new BoxLayout(centerTableOfCenterPanel, BoxLayout.Y_AXIS));
        //centerTableOfCenterPanel.add(mounthCalculate());
        //centerTableOfCenterPanel.add(new JLabel("sd"));
        //centerTableOfCenterPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
//        for (int i = 1; i <= 12; i++){
//            centerTableOfCenterPanel.add(new JLabel("" + i));
//        }
        JTextField[] sickDaysTextField = new JTextField[6]; // больничные отпускные дни
        JTextField[] sumOfActualSalaryTextField = new JTextField[6]; //Сумма фактического заработка

        // Получение даты из JXDatePicker
       // Date selectedDate = startDatePicker.getDate();
        // преобразование объекта типа Date в объект типа LocalDate


        JPanel buttomTableOfCentalPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        //buttomTableOfCentalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttomTableOfCentalPanel.add(new JLabel("Итого:"));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));
        buttomTableOfCentalPanel.add(new JLabel("..."));

        centerPanel.add(buttomTableOfCentalPanel,BorderLayout.SOUTH);

        JPanel totalPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        //totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        totalPanel.add(new JLabel("<html>Месяц<br> нетрудоспособности</html>"));
        totalPanel.add(new JLabel("<html>За дни в размере<br> 80% заработка</html>"));
        totalPanel.add(new JLabel("<html>За дни в размере<br> 100% заработка</html>"));
        for (int i = 1; i <= 3; i++) {
            totalPanel.add(new JLabel("" + i));
        }


        // создаем нижнюю панель с кнопками
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalPanel,BorderLayout.NORTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // добавляем кнопки
        JButton calculationButton = new JButton("Расчёт");
        calculationButton.setBackground(new Color(27, 161, 226));
        calculationButton.setForeground(new Color(255, 255, 255));
        JButton saveButton = new JButton("Сохранить");
        saveButton.setBackground(new Color(27, 161, 226));
        saveButton.setForeground(new Color(255, 255, 255));
        JButton cancelButton = new JButton("Отмена");
        cancelButton.setBackground(new Color(226, 27, 34));
        cancelButton.setForeground(new Color(255, 255, 255));
        calculationButton.setVisible(false);
        saveButton.setVisible(false);
        bottomPanel.add(calculationButton, BorderLayout.WEST);

        // создаем панель для кнопок справа
        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        rightButtonPanel.add(saveButton);
        rightButtonPanel.add(cancelButton);
        centerPanel.setBackground(Color.green);

        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);

        // добавляем все панели на основную панель окна
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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
                    calculationButton.setVisible(true);
                    //saveButton.setVisible(true);
                    setSize(750,700);
                    setLocationRelativeTo(parent);
                    LocalDate localDate = startDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    List<String> pastMonths = MonthUtils.getPastSixMonths(localDate);
                    List<Integer> daysInMonths = MonthUtils.getDaysInPastSixMonths(localDate);

                    for (int i = 0; i <= 5; i++) {
                        //centerTableOfCenterPanel.add(new JLabel("мес" + i));
                        centerTableOfCenterPanel.add(new JLabel(pastMonths.get(i))); // месяц
                        centerTableOfCenterPanel.add(new JLabel("" + daysInMonths.get(i))); // количество дней
                        sickDaysTextField[i] = new JTextField();
                        sickDaysTextField[i].setName("sickDaysTextField" + (i));
                        centerTableOfCenterPanel.add(sickDaysTextField[i]);
                        centerTableOfCenterPanel.add(new JLabel("осткд" + i));
                        sumOfActualSalaryTextField[i] = new JTextField();
                        sumOfActualSalaryTextField[i].setName("sumOfActualSalaryTextField" + (i));
                        centerTableOfCenterPanel.add(sumOfActualSalaryTextField[i]);
                        centerTableOfCenterPanel.add(new JLabel("сфз" + i));

                    }
                    centerPanel.add(centerTableOfCenterPanel, BorderLayout.CENTER);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage());
            } catch (InvalidDateRangeException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });



        cancelButton.addActionListener(e -> {
            dispose(); // закрытие окна при нажатии кнопки
        });

        // добавляем основную панель на окно и отображаем его
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
