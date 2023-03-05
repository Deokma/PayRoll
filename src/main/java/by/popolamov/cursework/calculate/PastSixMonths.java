package by.popolamov.cursework.calculate; /**
 * @author Denis Popolamov
 */

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PastSixMonths {

    /**
     * Класс для расчёта последних 6 месяцев
     */
    public static Component mounthCalculate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        for (int i = 1; i <= 6; i++) {
            LocalDate sixMonthsAgo = now.minusMonths(i);
            String month = sixMonthsAgo.format(formatter);
            System.out.println(month);
            //return month;
        }
        return null;
    }
}
