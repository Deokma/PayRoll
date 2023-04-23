package by.popolamov.cursework.listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * @author Denis Popolamov
 */


public class WindowCloseListener implements ActionListener {

    private JFrame frame;
    private Timer timer;

    public WindowCloseListener(JFrame frame) {
        this.frame = frame;
        this.timer = new Timer(60000, this); // задаем периодичность выполнения задачи в 60 секунд
        this.timer.start(); // запускаем таймер
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // метод вызывается каждые 60 секунд
        // проверяем, активно ли окно (т.е. находится ли в фокусе у пользователя)
            frame.dispose(); // закрываем окно
            timer.stop(); // останавливаем таймер

    }
}
