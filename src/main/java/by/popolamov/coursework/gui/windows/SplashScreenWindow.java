package by.popolamov.coursework.gui.windows;

import by.popolamov.coursework.listeners.WindowCloseListener;

import javax.swing.*;
import java.awt.*;

/**
 * Окно SplashScreen
 *
 * @author Denis Popolamov
 */
public class SplashScreenWindow extends JFrame {

    public SplashScreenWindow() {
        // Установка параметров окна
        setTitle("Приложение \"Расчёт начисления заработной платы за дни временной нетрудоспособности\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png");
        //ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("resources/images/icon.png"));
        setIconImage(icon.getImage());

        // setting panels
        JPanel pnlMain = new JPanel(new GridLayout(4, 1));
        JPanel pnlTop = new JPanel(new BorderLayout());
        JPanel pnlTitle = new JPanel(new BorderLayout());
        JPanel pnlMiddle = new JPanel(new GridLayout(1, 2, 15, 0));
        JPanel pnlBottom = new JPanel(new BorderLayout());

        // setting top panel
        Font mainFont = new Font("Arial", Font.BOLD, 14);

        JLabel lblBNTU = new JLabel("Белорусский национальный технический университет");
        lblBNTU.setHorizontalAlignment(SwingConstants.CENTER);
        lblBNTU.setFont(mainFont);
        JPanel pnlBNTU = new JPanel();
        pnlBNTU.setPreferredSize(new Dimension(WIDTH, 40));
        lblBNTU.setFont(new Font("Arial", Font.BOLD, 18)); // увеличение размера текста
        pnlBNTU.add(lblBNTU);

        JLabel lblFac = new JLabel("Факультет информационных технологий и робототехники");
        lblFac.setHorizontalAlignment(SwingConstants.CENTER);
        lblFac.setFont(mainFont);
        JPanel pnlFac = new JPanel();
        pnlFac.setPreferredSize(new Dimension(WIDTH, 20));
        lblFac.setFont(new Font("Arial", Font.BOLD, 18)); // увеличение размера текста
        pnlFac.add(lblFac);

        JLabel lblDep = new JLabel("Кафедра программного обеспечения информационных систем и технологий");
        lblDep.setHorizontalAlignment(SwingConstants.CENTER);
        lblDep.setFont(mainFont);
        JPanel pnlDep = new JPanel();
        pnlDep.setPreferredSize(new Dimension(WIDTH, 50));
        lblDep.setFont(new Font("Arial", Font.BOLD, 18)); // увеличение размера текста
        pnlDep.add(lblDep);

        pnlTop.add(pnlBNTU, BorderLayout.NORTH);
        pnlTop.add(pnlFac, BorderLayout.CENTER);
        pnlTop.add(pnlDep, BorderLayout.SOUTH);

        // setting title panel
        JLabel lblCourseWork = new JLabel("Курсовая работа");
        lblCourseWork.setHorizontalAlignment(SwingConstants.CENTER);
        lblCourseWork.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel pnlCourseWork = new JPanel();
        pnlCourseWork.setPreferredSize(new Dimension(WIDTH, 30));
        pnlCourseWork.add(lblCourseWork);

        JLabel lblSub = new JLabel("по дисциплине \"Программирование на языке Java\"");
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        lblSub.setFont(mainFont);

        JLabel lblTheme = new JLabel("Расчёт начисления заработной платы за дни временной нетрудоспособности");
        lblTheme.setHorizontalAlignment(SwingConstants.CENTER);
        lblTheme.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel pnlTheme = new JPanel();
        pnlTheme.setPreferredSize(new Dimension(WIDTH, 70));
        pnlTheme.add(lblTheme);

        pnlTitle.add(pnlCourseWork, BorderLayout.NORTH);
        pnlTitle.add(lblSub, BorderLayout.CENTER);
        pnlTitle.add(pnlTheme, BorderLayout.SOUTH);

        // setting middle panel
        JPanel pnlMiddleLeft = new JPanel();
        JPanel pnlMiddleRight = new JPanel(new GridLayout(2, 1, 30, 0));

        // setting left panel
        ImageIcon imgMain = new ImageIcon("src/main/resources/images/icon-dark.png");
        //ImageIcon imgMain = new ImageIcon(ClassLoader.getSystemResource("resources/images/icon-dark.png"));
        JLabel lblMainImage = new JLabel(
                new ImageIcon(imgMain.getImage().getScaledInstance(130, 110, Image.SCALE_SMOOTH)));
        pnlMiddleLeft.add(lblMainImage);

        // setting right panel
        JLabel lblStudent = new JLabel("<html>Выполнил: студент группы 10702420" +
                "<br/>Пополамов Денис Вячеславович<html>");
        lblStudent.setFont(mainFont);
        JLabel lblTeacher = new JLabel("<html>Преподаватель: к.ф.-м.н., доц." +
                "<br/>Сидорик Валерий Владимирович<html>");
        lblTeacher.setFont(mainFont);
        pnlMiddleRight.add(lblStudent);
        pnlMiddleRight.add(lblTeacher);

        pnlMiddle.add(pnlMiddleLeft);
        pnlMiddle.add(pnlMiddleRight);

        // setting bottom panel
        JLabel lblCity = new JLabel("Минск 2023");

        lblCity.setFont(new Font("Arial", Font.BOLD, 20));
        lblCity.setHorizontalAlignment(SwingConstants.CENTER);
        lblCity.setVerticalAlignment(JLabel.BOTTOM);
        lblCity.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel pnlCity = new JPanel();

        pnlCity.setPreferredSize(new Dimension(60, 60));

        pnlCity.add(lblCity);

        JPanel pnlButtons = new JPanel(new FlowLayout());

        JButton btnNext = new JButton("Далее");

        btnNext.addActionListener(e -> {
            try {
                MainWindow form = new MainWindow();
                dispose();
                form.setVisible(true);
                // new BluePanelWindow();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка выполнения программы: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton btnExit = new JButton("Выход");
        btnExit.addActionListener(e -> System.exit(0));

        btnNext.setFont(new Font("Arial", Font.BOLD, 20)); // увеличение размера текста кнопки "Дальше"
        btnExit.setFont(new Font("Arial", Font.BOLD, 20)); // увеличение размера текста кнопки "Выйти"

        btnNext.setBackground(new Color(27, 161, 226));
        btnExit.setBackground(new Color(27, 161, 226));

        btnNext.setForeground(new Color(255, 255, 255));
        btnExit.setForeground(new Color(255, 255, 255));

        pnlButtons.add(btnNext);
        pnlButtons.add(btnExit);

        pnlBottom.add(pnlCity, BorderLayout.NORTH);
        pnlBottom.add(pnlButtons, BorderLayout.SOUTH);

        // setting main panel
        pnlMain.add(pnlTop);
        pnlMain.add(pnlTitle);
        pnlMain.add(pnlMiddle);
        pnlMain.add(pnlBottom);
        pnlMain.setPreferredSize(new Dimension(700, 500));
        add(pnlMain);

        WindowCloseListener closeListener = new WindowCloseListener(this);
    }
}
