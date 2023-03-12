package by.popolamov.cursework.calculate;

/**
 * @author Denis Popolamov
 */

import org.jdesktop.swingx.JXDatePicker;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

    public static List<String> getPastSixMonths(LocalDate date) {
        List<String> pastMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i + 1));
            pastMonths.add(yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        }
        return pastMonths;
    }

    public static List<Integer> getDaysInPastSixMonths(LocalDate date) {
        List<Integer> daysInMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i + 1));
            daysInMonths.add(yearMonth.lengthOfMonth());
        }
        return daysInMonths;
    }

    public static int getTotalDaysInPastSixMonths(LocalDate date) {
        List<Integer> daysInMonths = getDaysInPastSixMonths(date);
        int totalDays = 0;
        for (int days : daysInMonths) {
            totalDays += days;
        }
        return totalDays;
    }

    public static int calculateTotalRemainingDays(List<Integer> remainingDaysList) {
        return remainingDaysList.stream().mapToInt(Integer::intValue).sum();
    }
    //метод stream() превращает список в поток данных,
    // mapToInt() преобразует каждый элемент потока в целое число,
    // sum() суммирует все элементы потока и возвращает итоговую сумму.

    public static String getCurrentMonth(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL");
        return date.format(formatter);
    }

    public static int getDaysBetweenDates(JXDatePicker startDatePicker, JXDatePicker endDatePicker) {
        LocalDate startDate = startDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(startDate, endDate).getDays();
    }

}
