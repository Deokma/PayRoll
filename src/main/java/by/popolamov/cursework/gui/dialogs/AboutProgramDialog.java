package by.popolamov.cursework.gui.dialogs;

/**
 * Диалоговое окно "О программе"
 *
 * @author Denis Popolamov
 */

import javax.swing.*;
import java.awt.*;

public class AboutProgramDialog extends JDialog {

    public AboutProgramDialog(JFrame parent) {
        super(parent, "О программе", true);
        setPreferredSize(new Dimension(600, 300));

        setResizable(false);
        setLocationRelativeTo(null);

        // Создание надписи "О программе" и добавление на панель
        JLabel lblTitle = new JLabel("Приложение " +
                "\"Расчёт начисления заработной платы за дни временной нетрудоспособности\"");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        add(lblTitle, BorderLayout.NORTH);
        // Создание панели для картинки и текста
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Создание картинки и добавление на панель
        //ImageIcon imgIcon = new ImageIcon("src/main/resources/images/icon-dark.png");
        ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("resources/images/icon-dark.png"));
        Image image = imgIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(image));
        pnlMain.add(lblImage, BorderLayout.WEST);

        Font mainFont = new Font("Helvetica", Font.PLAIN, 14);

        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        // Создание текстового поля и добавление на панель
        JTextArea txaInfo = new JTextArea(
                "Программа позволяет:" +
                        "\n" +
                        "1. Создание расчёта заработной платы за период нетрудоспособности.\n" +
                        "2. Сохранение расчёта заработной платы за период нетрудоспособности в базу данных.\n" +
                        "3. Просмотреть детали расчёта.\n" +
                        "4. Сохранить расчёт в Word, Excel, PDF."
        );
        txaInfo.setEditable(false);
        txaInfo.setLineWrap(true);
        txaInfo.setFont(mainFont);
        txaInfo.setWrapStyleWord(true);

        JScrollPane scrInfo = new JScrollPane(txaInfo);
        scrInfo.setPreferredSize(new Dimension(0, 200));
        pnlRight.add(scrInfo, BorderLayout.CENTER);

        JPanel pnlButtom = new JPanel(new BorderLayout());
        // Создаем лейбл с версией программы и добавляем его в панель
        JLabel lblVersion = new JLabel("Версия программы 1.1.4");
        lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
        lblVersion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlButtom.add(lblVersion, BorderLayout.WEST);
        JPanel pnlBackButton = new JPanel(new GridLayout(1, 1, 0, 10));
        // Создание кнопки "Назад" и добавление на панель
        JButton backButton = new JButton("Назад");
        backButton.setBackground(new Color(226, 27, 34));
        backButton.setForeground(new Color(255, 255, 255));
        pnlButtom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        backButton.addActionListener(e -> dispose());
        pnlBackButton.add(backButton, BorderLayout.EAST);
        pnlButtom.add(pnlBackButton, BorderLayout.EAST);
        pnlRight.add(pnlButtom, BorderLayout.SOUTH);
        add(pnlRight, BorderLayout.SOUTH);

        pnlMain.add(pnlRight, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
        // завершение настройки окна
        pack();
        setVisible(true);
    }

}
