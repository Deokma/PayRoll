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
        setPreferredSize(new Dimension(600, 300));

        setResizable(false);
        setLocationRelativeTo(null);

        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Создание текстового поля и добавление на панель
        JTextArea txaHelpInfo = new JTextArea("Много текста о программе");
        txaHelpInfo.setEditable(false);
        txaHelpInfo.setLineWrap(true);
        txaHelpInfo.setWrapStyleWord(true);
        JScrollPane scr = new JScrollPane(txaHelpInfo);
        scr.setPreferredSize(new Dimension(0, 200));
        pnlRight.add(scr, BorderLayout.CENTER);

        JPanel pnlButtom = new JPanel(new BorderLayout());

        JPanel pnlBackButton = new JPanel(new GridLayout(1, 1, 20, 10));
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

        pnl.add(pnlRight, BorderLayout.CENTER);
        add(pnl, BorderLayout.CENTER);
        // завершение настройки окна
        pack();
        setVisible(true);
    }
}
