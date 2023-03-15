package by.popolamov.cursework.calculate;

import org.jdesktop.swingx.JXDatePicker;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс расчёта дат
 *
 * @author Denis Popolamov
 */
public class DateUtils {

    /**
     * Получение List 6-и предыдущих месяцев от даты
     *
     * @param date дата
     * @return вывод List 6 предыдущих месяцев
     */
    public static List<String> getPastSixMonths(LocalDate date) {
        List<String> pastMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i + 1));
            pastMonths.add(yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        }
        return pastMonths;
    }

    /**
     * Получение List дней из предыдущих 6-и месяцев
     *
     * @param date дата
     * @return List дней из предыдущих 6-и месяцев
     */
    public static List<Integer> getDaysInPastSixMonths(LocalDate date) {
        List<Integer> daysInMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i + 1));
            daysInMonths.add(yearMonth.lengthOfMonth());
        }
        return daysInMonths;
    }

    /**
     * Расчёт суммы всех дней из предыдущих 6-и месяцев
     *
     * @param date дата
     * @return общее количество дней в предыдущих 6-и месяцах
     */
    public static int getTotalDaysInPastSixMonths(LocalDate date) {
        List<Integer> daysInMonths = getDaysInPastSixMonths(date);
        int totalDays = 0;
        for (int days : daysInMonths) {
            totalDays += days;
        }
        return totalDays;
    }

    /**
     * Для расчёта суммы всех рабочих дней за предыдущие 6 месяцев
     *
     * @param remainingDaysList List рабочих дней
     * @return общая сумма рабочих дней за предыдущие 6 месяцев
     */
    public static int calculateTotalRemainingDays(List<Integer> remainingDaysList) {
        return remainingDaysList.stream().mapToInt(Integer::intValue).sum();
    }
    //метод stream() превращает список в поток данных,
    // mapToInt() преобразует каждый элемент потока в целое число,
    // sum() суммирует все элементы потока и возвращает итоговую сумму.

    /**
     * Получение месяца по дате
     *
     * @param date дата
     * @return месяц
     */
    public static String getCurrentMonth(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL");
        return date.format(formatter);
    }

    /**
     * Получение количество дней между двумя датами
     * 
     * @param firstDate
     * @param endDatePicker
     * @return
     */
    public static int getDaysBetweenDates(JXDatePicker firstDate, JXDatePicker endDatePicker) {
        LocalDate startDate = firstDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(startDate, endDate).getDays();
    }

}
