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
        JLabel lblTitle = new JLabel("О программе");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        add(lblTitle, BorderLayout.NORTH);
        // Создание панели для картинки и текста
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Создание картинки и добавление на панель
        ImageIcon imgIcon = new ImageIcon("src/main/resources/images/icon-dark.png");
        Image image = imgIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(image));
        pnlMain.add(lblImage, BorderLayout.WEST);

        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        // Создание текстового поля и добавление на панель
        JTextArea txaInfo = new JTextArea(
                "Программа \"Расчет начисления платы за дни временной нетрудоспособности\" - " +
                        "это эффективное решение для расчета начисления за выплаты пособий в случае " +
                        "временной нетрудоспособности работников.\n" +
                        "\n" +
                        "Эта программа оснащена всеми необходимым для расчета пособий в соответствии с законодательством Республики Беларусь. " +
                        "Она позволяет легко вводить данные о начислениях и сохранять результаты расчета в базе данных. " +
                        "Благодаря этому вы можете легко отслеживать и анализировать историю начислений.\n" +
                        "\n" +
                        "Программа генерирует отчеты о начислениях, которые можно сохранять в " +
                        "различных форматах, таких как Word, Excel или PDF," +
                        " и легко экспортировать для дальнейшей работы.\n" +
                        "\n" +
                        "С помощью этой программы вы сможете точно рассчитать начисления платы за дни временной нетрудоспособности," +
                        " что ваша компания выплачивает своим сотрудникам все соответствующие пособия, сокращая риски ошибок и конфликтов.\n"
        );
        txaInfo.setEditable(false);
        txaInfo.setLineWrap(true);
        txaInfo.setWrapStyleWord(true);
        JScrollPane scrInfo = new JScrollPane(txaInfo);
        scrInfo.setPreferredSize(new Dimension(0, 200));
        pnlRight.add(scrInfo, BorderLayout.CENTER);

        JPanel pnlButtom = new JPanel(new BorderLayout());
        // Создаем лейбл с версией программы и добавляем его в панель
        JLabel lblVersion = new JLabel("Версия программы 1.1");
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
