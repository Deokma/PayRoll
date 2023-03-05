package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.swingx.*;

public class DatePickerExample extends JFrame {

    private JLabel dateLabel;
    private JXDatePicker datePicker;
    private JButton showButton;
    private JLabel resultLabel1;
    private JLabel resultLabel2;

    public DatePickerExample() {
        // Настройка окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Пример DatePicker");
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Создание элементов
        dateLabel = new JLabel("Выберите дату:");
        datePicker = new JXDatePicker();
        showButton = new JButton("Показать результат");
        resultLabel1 = new JLabel("Результат 1");
        resultLabel2 = new JLabel("Результат 2");

        // Настройка компоновки
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(dateLabel);
        mainPanel.add(datePicker);
        mainPanel.add(showButton);
        mainPanel.add(resultLabel1);
        mainPanel.add(resultLabel2);
        add(mainPanel);

        // Назначение обработчика событий кнопки
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Проверка, что дата выбрана
                if (datePicker.getDate() != null) {
                    // Отображение результатов
                    resultLabel1.setText("Результат 1 для " + datePicker.getDate());
                    resultLabel2.setText("Результат 2 для " + datePicker.getDate());
                    resultLabel1.setVisible(true);
                    resultLabel2.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(DatePickerExample.this,
                            "Пожалуйста, выберите дату");
                }
            }
        });

        // Скрытие результатов при запуске приложения
        resultLabel1.setVisible(false);
        resultLabel2.setVisible(false);

        setVisible(true);
    }

    public static void main(String[] args) {
        new DatePickerExample();
    }
}
