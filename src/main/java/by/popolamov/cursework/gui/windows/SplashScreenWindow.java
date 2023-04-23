package by.popolamov.cursework.gui.windows;

import by.popolamov.cursework.listeners.WindowCloseListener;

import javax.swing.*;
import java.awt.*;
/**
 *
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
        setIconImage(icon.getImage());

        // setting panels
        JPanel jpMain = new JPanel(new GridLayout(4, 1));
        JPanel jpTop = new JPanel(new BorderLayout());
        JPanel jpTitle = new JPanel(new BorderLayout());
        JPanel jpMiddle = new JPanel(new GridLayout(1, 2, 15, 0));
        JPanel jpBottom = new JPanel(new BorderLayout());

        // setting top panel
        Font mainFont = new Font("Arial", Font.BOLD, 14);

        JLabel jlBNTU = new JLabel("Белорусский национальный технический университет");
        jlBNTU.setHorizontalAlignment(SwingConstants.CENTER);
        jlBNTU.setFont(mainFont);
        JPanel jpBNTU = new JPanel();
        jpBNTU.setPreferredSize(new Dimension(WIDTH, 40));
        jlBNTU.setFont(new Font("Arial", Font.BOLD, 18)); // увеличение размера текста
        jpBNTU.add(jlBNTU);

        JLabel jlFac = new JLabel("Факультет информационных технологий и робототехники");
        jlFac.setHorizontalAlignment(SwingConstants.CENTER);
        jlFac.setFont(mainFont);
        JPanel jpFac = new JPanel();
        jpFac.setPreferredSize(new Dimension(WIDTH, 20));
        jlFac.setFont(new Font("Arial", Font.BOLD, 18)); // увеличение размера текста
        jpFac.add(jlFac);

        JLabel jlDep = new JLabel("Кафедра программаного обеспечения информационных систем и технологий");
        jlDep.setHorizontalAlignment(SwingConstants.CENTER);
        jlDep.setFont(mainFont);
        JPanel jpDep = new JPanel();
        jpDep.setPreferredSize(new Dimension(WIDTH, 50));
        jlDep.setFont(new Font("Arial", Font.BOLD, 18)); // увеличение размера текста
        jpDep.add(jlDep);

        jpTop.add(jpBNTU, BorderLayout.NORTH);
        jpTop.add(jpFac, BorderLayout.CENTER);
        jpTop.add(jpDep, BorderLayout.SOUTH);

        // setting title panel
        JLabel jlCW = new JLabel("Курсовая работа");
        jlCW.setHorizontalAlignment(SwingConstants.CENTER);
        jlCW.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel jpCW = new JPanel();
        jpCW.setPreferredSize(new Dimension(WIDTH, 30));
        jpCW.add(jlCW);

        JLabel jlSub = new JLabel("по дисциплине \"Программирование на языке Java\"");
        jlSub.setHorizontalAlignment(SwingConstants.CENTER);
        jlSub.setFont(mainFont);

        JLabel jlTheme = new JLabel("Расчёт начисления заработной платы за дни временной нетрудоспособности");
        jlTheme.setHorizontalAlignment(SwingConstants.CENTER);
        jlTheme.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel jpTheme = new JPanel();
        jpTheme.setPreferredSize(new Dimension(WIDTH, 70));
        jpTheme.add(jlTheme);

        jpTitle.add(jpCW, BorderLayout.NORTH);
        jpTitle.add(jlSub, BorderLayout.CENTER);
        jpTitle.add(jpTheme, BorderLayout.SOUTH);

        // setting middle panel
        JPanel jpMiddleLeft = new JPanel();
        JPanel jpMiddleRight = new JPanel(new GridLayout(2, 1, 30, 0));

        // setting left panel
        ImageIcon img = new ImageIcon("src/main/resources/images/icon-dark.png");
        JLabel jlImage = new JLabel(new ImageIcon(img.getImage().getScaledInstance(130, 110, Image.SCALE_SMOOTH)));
        jpMiddleLeft.add(jlImage);

        // setting right panel
        JLabel jlStudent = new JLabel("<html>Выполнил: студент группы 10702420" +
                "<br/>Пополамов Денис Вячеславович<html>");
        jlStudent.setFont(mainFont);
        JLabel jlTeacher = new JLabel("<html>Преподаватель: к.ф.-м.н., доц." +
                "<br/>Сидорик Валерий Владимирович<html>");
        jlTeacher.setFont(mainFont);
        jpMiddleRight.add(jlStudent);
        jpMiddleRight.add(jlTeacher);

        jpMiddle.add(jpMiddleLeft);
        jpMiddle.add(jpMiddleRight);

        // setting bottom panel
        JLabel jlCity = new JLabel("Минск 2023");

        jlCity.setFont(new Font("Arial", Font.BOLD, 20));
        jlCity.setHorizontalAlignment(SwingConstants.CENTER);
        jlCity.setVerticalAlignment(JLabel.BOTTOM);
        jlCity.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel jpCity = new JPanel();

        jpCity.setPreferredSize(new Dimension(60, 60));

        jpCity.add(jlCity);

        JPanel jpButtons = new JPanel(new FlowLayout());

        JButton jbNext = new JButton("Далее");
        jbNext.addActionListener(e -> {
            dispose();
            MainWindow form = new MainWindow();
            form.setVisible(true);
            // new BluePanelWindow();
        });
        JButton jbExit = new JButton("Выход");
        jbExit.addActionListener(e -> System.exit(0));

        jbNext.setFont(new Font("Arial", Font.BOLD, 20)); // увеличение размера текста кнопки "Дальше"
        jbExit.setFont(new Font("Arial", Font.BOLD, 20)); // увеличение размера текста кнопки "Выйти"

        jbNext.setBackground(new Color(27, 161, 226));
        jbExit.setBackground(new Color(27, 161, 226));

        jbNext.setForeground(new Color(255, 255, 255));
        jbExit.setForeground(new Color(255, 255, 255));

        jpButtons.add(jbNext);
        jpButtons.add(jbExit);

        jpBottom.add(jpCity, BorderLayout.NORTH);
        jpBottom.add(jpButtons, BorderLayout.SOUTH);

        // setting main panel
        jpMain.add(jpTop);
        jpMain.add(jpTitle);
        jpMain.add(jpMiddle);
        jpMain.add(jpBottom);
        jpMain.setPreferredSize(new Dimension(700, 500));
        add(jpMain);

        WindowCloseListener closeListener = new WindowCloseListener(this);
    }

//
//    public static void main(String[] args) {
//        SplashScreen form = new SplashScreen();
//        form.setVisible(true);
//    }
}
