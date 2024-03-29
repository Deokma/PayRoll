package by.popolamov.coursework.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Диалоговое окно об авторе
 *
 * @author Denis Popolamov
 */
public class AboutAuthorDialog extends JDialog {
    public AboutAuthorDialog(JFrame parent) throws IOException {
        super(parent, "Об авторе", true);
        // установка размеров и запрет изменения размеров окна
        setPreferredSize(new Dimension(260, 400));
        setResizable(false);
        setLocationRelativeTo(parent);

        // создание панели содержимого окна
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.PAGE_AXIS));
        setContentPane(pnlContent);

        // создание элементов окна
        ImageIcon authorImageIcon = new ImageIcon("src/main/resources/images/author.jpg");
        //ImageIcon authorImageIcon = new ImageIcon(ClassLoader.getSystemResource("resources/images/author.jpg"));

        JLabel lblAuthorImage = new JLabel(
                new ImageIcon(authorImageIcon.getImage().getScaledInstance(200, 240, Image.SCALE_SMOOTH)));
        JLabel lblGroup = new JLabel("Студент группы 10702420");
        JLabel lblName = new JLabel("Пополамов Денис Вячеславович");
        JLabel lblEmail = new JLabel("dpopolamovmail@gmail.com");
        JButton btnBack = new JButton("Назад");

        Font textFont = new Font("Helvetica", Font.PLAIN, 14);
        lblGroup.setFont(textFont);
        lblName.setFont(textFont);
        lblEmail.setFont(textFont);

        btnBack.setFont(new Font("Helvetica", Font.BOLD, 20));

        btnBack.setBackground(new Color(27, 161, 226));

        btnBack.setForeground(new Color(255, 255, 255));

        // установка выравнивания элементов по центру
        lblAuthorImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGroup.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        // добавление элементов на панель содержимого окна
        pnlContent.add(Box.createVerticalGlue()); // пространство сверху
        pnlContent.add(lblAuthorImage);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 20))); // отступ между изображением и текстом
        pnlContent.add(lblGroup);
        pnlContent.add(lblName);
        pnlContent.add(lblEmail);
        pnlContent.add(Box.createVerticalGlue()); // пространство между текстом и кнопкой
        pnlContent.add(btnBack);
        pnlContent.add(Box.createVerticalGlue()); // пространство снизу

        // установка обработчика событий для кнопки "Назад"
        btnBack.addActionListener(e -> {
            dispose(); // закрытие окна при нажатии кнопки
        });

        // установка позиции окна по центру экрана
        setLocationRelativeTo(null);

        // завершение настройки окна
        pack();
        setVisible(true);
    }
}
