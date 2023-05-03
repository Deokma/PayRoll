package by.popolamov.cursework.gui.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Диалоговое окно "Помощь"
 *
 * @author Denis Popolamov
 */
public class HelpDialog extends JDialog {
    public HelpDialog(JFrame parent) {
        super(parent, "Помощь", true);
        setPreferredSize(new Dimension(600, 600));

        setResizable(false);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Создание текстового поля и добавление на панель
        JTextArea txaHelpInfo = new JTextArea(
                "Добро пожаловать в окно Help программы \"Расчёт начисления платы за дни временной нетрудоспособности\"!\n" +
                        "\n" +
                        "Эта программа позволяет автоматически рассчитывать начисление платы работникам за дни временной нетрудоспособности." +
                        " Для начала расчёта нужно нажать на кнопку \"Добавить\" в главном окне, после чего ввести данные работника:" +
                        " ФИО и период болезни. После заполнения этой информации необходимо нажать кнопку \"Подтвердить\"," +
                        " чтобы продолжить расчёт.\n" +
                        "\n" +
                        "Далее программа запросит у пользователя данные о зарплате работника за предыдущие 6 месяцев и количество" +
                        " дней нетрудоспособности, если таковые имеются. Если пользователь не заполнит все поля, " +
                        "и нажмёт кнопку \"Расчёт\", программа автоматически заполнит их нулями.\n" +
                        "\n" +
                        "Расчёт начисления платы происходит после нажатия на кнопку \"Расчёт\", и выводится в том же окне." +
                        " В случае необходимости, пользователь может сохранить данные о расчёте, используя кнопку \"Сохранить\".\n" +
                        "\n" +
                        "Кроме того, в главном окне пользователь может просмотреть детали расчёта, такие как количество дней," +
                        " сумму начисления и другие параметры. Это может быть полезно для того, чтобы убедиться в правильности" +
                        " расчёта или для отчётности перед работодателем или страховой компанией.\n" +
                        "\n" +
                        "Также программа позволяет сохранить результаты расчёта в файлы Word, Excel или PDF, что удобно для архивирования данных," +
                        " печати или отправки по электронной почте. Все файлы сохраняются в выбранной пользователем директории и могут" +
                        " быть легко найдены и открыты в дальнейшем. \n" +
                        "\n" +
                        "Также в программе есть возможность поменять базу данных, в которой хранятся данные о работниках и начислениях." +
                        " Для этого откройте файл \"database.properties\" который находится в директории \"src\\main\\resources\"" +
                        " и измените настройки под свои нужды. " +
                        "Обратите внимание, что изменение базы данных может привести к потере всех ранее сохраненных данных." +
                        " Перезапустите программу после внесения изменений в файл \"database.properties\".\n" +
                        "\n" +
                        "Надеемся, что данное окно Help поможет вам разобраться с программой" +
                        " \"Расчёт начисления платы за дни временной нетрудоспособности\".");
        txaHelpInfo.setEditable(false);
        txaHelpInfo.setLineWrap(true);
        txaHelpInfo.setWrapStyleWord(true);
        JScrollPane scr = new JScrollPane(txaHelpInfo);
        scr.setPreferredSize(new Dimension(0, 200));
        pnlRight.add(scr, BorderLayout.CENTER);

        JPanel pnlButtom = new JPanel(new BorderLayout());

        JPanel pnlBackButton = new JPanel(new GridLayout(1, 1, 20, 10));
        // Создание кнопки "Назад" и добавление на панель
        JButton btnBack = new JButton("Назад");
        btnBack.setBackground(new Color(226, 27, 34));
        btnBack.setForeground(new Color(255, 255, 255));
        pnlButtom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        btnBack.addActionListener(e -> dispose());
        pnlBackButton.add(btnBack, BorderLayout.EAST);
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
