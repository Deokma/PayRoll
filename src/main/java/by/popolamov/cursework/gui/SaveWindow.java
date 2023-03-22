package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */

import javax.swing.*;
import java.awt.*;

public class SaveWindow extends JDialog {

    public SaveWindow(JDialog parent) {
        setTitle("SaveWindow");
        setPreferredSize(new Dimension(300, 120));
        setResizable(false);
        setLocationRelativeTo(parent);

        // Создаем панель для кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Создаем кнопки
        JButton wordButton = new JButton("Word");
        JButton excelButton = new JButton("Excel");
        JButton pdfButton = new JButton("PDF");

        // Устанавливаем цвета кнопок
        wordButton.setBackground(new Color(51, 153, 255));
        wordButton.setForeground(Color.WHITE);
        excelButton.setBackground(new Color(51, 255, 51));
        excelButton.setForeground(Color.WHITE);
        pdfButton.setBackground(new Color(255, 51, 51));
        pdfButton.setForeground(Color.WHITE);

        // Добавляем кнопки на панель
        buttonPanel.add(wordButton);
        buttonPanel.add(excelButton);
        buttonPanel.add(pdfButton);

        // Создаем жирный Label для заголовка
        JLabel titleLabel = new JLabel("Сохранить в");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Добавляем Label и панель на окно
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Отображаем окно
        pack();
        setVisible(true);
    }
}
