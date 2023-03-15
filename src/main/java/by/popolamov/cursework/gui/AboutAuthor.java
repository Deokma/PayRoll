package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */
import javax.swing.*;
import java.awt.*;

public class AboutAuthor extends JDialog {
    public AboutAuthor(JFrame parent) {
        super(parent, "Об авторе", true);
        // установка размеров и запрет изменения размеров окна
        setPreferredSize(new Dimension(260, 370));
        setResizable(false);
        setLocationRelativeTo(parent);

        // создание панели содержимого окна
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);

        // создание элементов окна
        ImageIcon authorImageIcon = new ImageIcon("src/main/resources/images/author.jpg");
        JLabel jlauthorImage = new JLabel(new ImageIcon(authorImageIcon.getImage().getScaledInstance(170, 195, Image.SCALE_SMOOTH)));
        JLabel groupLabel = new JLabel("Студент группы 10702420");
        JLabel nameLabel = new JLabel("Пополамов Денис Вячеславович");
        JLabel emailLabel = new JLabel("dpopolamovmail@gmail.com");
        JButton backButton = new JButton("Назад");

        Font textFont = new Font("Helvetica", Font.PLAIN, 14);
        groupLabel.setFont(textFont);
        nameLabel.setFont(textFont);
        emailLabel.setFont(textFont);


        backButton.setFont(new Font("Helvetica", Font.BOLD, 20));

        backButton.setBackground(new Color(27, 161, 226));

        backButton.setForeground(new Color(255, 255, 255));

        // установка выравнивания элементов по центру
        jlauthorImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        groupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // добавление элементов на панель содержимого окна
        contentPane.add(Box.createVerticalGlue()); // пространство сверху
        contentPane.add(jlauthorImage);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20))); // отступ между изображением и текстом
        contentPane.add(groupLabel);
        contentPane.add(nameLabel);
        contentPane.add(emailLabel);
        contentPane.add(Box.createVerticalGlue()); // пространство между текстом и кнопкой
        contentPane.add(backButton);
        contentPane.add(Box.createVerticalGlue()); // пространство снизу

        // установка обработчика событий для кнопки "Назад"
        backButton.addActionListener(e -> {
            dispose(); // закрытие окна при нажатии кнопки
        });

        // установка позиции окна по центру экрана
        setLocationRelativeTo(null);

        // завершение настройки окна
        pack();
        setVisible(true);
    }
}
