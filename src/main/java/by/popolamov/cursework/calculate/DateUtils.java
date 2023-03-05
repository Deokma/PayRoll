//package by.popolamov.cursework.calculate;
//
///**
// * @author Denis Popolamov
// */
//import org.jdesktop.swingx.JXDatePicker;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DateUtils {
//
//    /**
//     * Метод для получения списка последних 6 месяцев относительно даты, выбранной в JXDatePicker.
//     *
//     * @param datePicker дата, выбранная в JXDatePicker.
//     * @return список последних 6 месяцев относительно выбранной даты.
//     */
//    public static List<String> getPastMonths(JXDatePicker datePicker) {
//        LocalDate selectedDate = datePicker.getDate().toInstant().atZone(datePicker.ZoneId()).toLocalDate();
//        List<String> pastMonths = new ArrayList<>();
//
//        for (int i = 5; i >= 0; i--) {
//            YearMonth yearMonth = YearMonth.from(selectedDate.minusMonths(i));
//            String monthYearString = yearMonth.format(DateTimeFormatter.ofPattern("LLLL yyyy"));
//            pastMonths.add(monthYearString);
//        }
//
//        return pastMonths;
//    }
//
//    /**
//     * Метод для получения количества дней в каждом месяце из списка месяцев.
//     *
//     * @param months список месяцев, для которых нужно получить количество дней.
//     * @return список количества дней в каждом месяце.
//     */
//    public static List<Integer> getDaysInMonths(List<String> months) {
//        List<Integer> daysInMonths = new ArrayList<>();
//
//        for (String month : months) {
//            YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("LLLL yyyy"));
//            int daysInMonth = yearMonth.lengthOfMonth();
//            daysInMonths.add(daysInMonth);
//        }
//
//        return daysInMonths;
//    }
//}
