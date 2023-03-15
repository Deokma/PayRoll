package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AboutProgram extends JDialog {

    public AboutProgram(JFrame parent) {
        super(parent, "О программе", true);
        setPreferredSize(new Dimension(600, 300));

        setResizable(false);
        setLocationRelativeTo(null);
        //setLayout(new BorderLayout());

        // Создание надписи "О программе" и добавление на панель
        JLabel titleLabel = new JLabel("О программе");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        add(titleLabel, BorderLayout.NORTH);
        // Создание панели для картинки и текста
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Создание картинки и добавление на панель
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon-dark.png");
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        //imageLabel.setPreferredSize(new Dimension(panel.getWidth() / 3, panel.getHeight()));
        panel.add(imageLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Создание текстового поля и добавление на панель
        JTextArea textArea = new JTextArea("Много текста о программе");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        rightPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel buttomPanel = new JPanel(new GridLayout(1, 2));
        buttomPanel.setBorder(BorderFactory.createEmptyBorder(60, 30, 0, 0));
        // Создаем лейбл с версией программы и добавляем его в панель
        JLabel versionLabel = new JLabel("Версия программы 1.0");
        versionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttomPanel.add(versionLabel, BorderLayout.WEST);
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        // Создание кнопки "Назад" и добавление на панель
        JButton backButton = new JButton("Назад");
        backButton.setBackground(new Color(226, 27, 34));
        backButton.setForeground(new Color(255, 255, 255));
        //buttomPanel.setBorder(BorderFactory.createEmptyBorder(70, 140, 0, 0));
        backButton.addActionListener(e -> dispose());
        backButtonPanel.add(backButton, BorderLayout.EAST);
        buttomPanel.add(backButtonPanel, BorderLayout.EAST);
        rightPanel.add(buttomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        // add(rightPanel, BorderLayout.EAST);
        // завершение настройки окна
        pack();
        setVisible(true);
    }

}
