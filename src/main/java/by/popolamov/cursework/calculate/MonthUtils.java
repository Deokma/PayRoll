package by.popolamov.cursework.calculate;

/**
 * @author Denis Popolamov
 */
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MonthUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<String> getPastSixMonths(LocalDate date) {
        List<String> pastMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i+1));
            pastMonths.add(yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        }
        return pastMonths;
    }

    public static List<Integer> getDaysInPastSixMonths(LocalDate date) {
        List<Integer> daysInMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i+1));
            daysInMonths.add(yearMonth.lengthOfMonth());
        }
        return daysInMonths;
    }
}
