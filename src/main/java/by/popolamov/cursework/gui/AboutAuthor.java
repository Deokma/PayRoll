package by.popolamov.cursework.gui;

/**
 * @author Denis Popolamov
 */
//import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AboutAuthor extends JDialog {
   // private JXImagePanel authorImageIcon;
    public AboutAuthor(JFrame parent) throws IOException {
        super(parent, "Об авторе", true);
        // установка размеров и запрет изменения размеров окна
        setPreferredSize(new Dimension(260, 370));
        setResizable(false);
        setLocationRelativeTo(parent);

        // создание панели содержимого окна
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.PAGE_AXIS));
        setContentPane(pnlContent);

        // создание элементов окна
        ImageIcon authorImageIcon = new ImageIcon("src/main/resources/images/author.jpg");
        BufferedImage image = ImageIO.read(new File("src/main/resources/images/author.jpg"));
        //JXImagePanel authorImageIcon = new JXImagePanel(image);
        JXImageView authorImageView = new JXImageView();
        authorImageView.setImage(authorImageView.getImage());

        //JLabel lblAuthorImage = new JLabel(new ImageIcon(authorImageIcon.getImage().getScaledInstance(170, 195, Image.SCALE_SMOOTH)));
        JLabel lblAuthorImage = new JLabel();
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
