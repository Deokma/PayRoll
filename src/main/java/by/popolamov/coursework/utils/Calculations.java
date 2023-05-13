package by.popolamov.coursework.utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс расчёта отпускных/больничных
 *
 * @author Denis Popolamov
 */
public class Calculations {
    /**
     * Расчёт рабочих дней за месяц.
     *
     * @param daysInMonths    Количество дней в месяце
     * @param sickDaysInMonth Количество больничных дней в месяце
     * @return Количество рабочих дней за месяц
     */
    public static List<Integer> calculateRemainingDays(List<Integer> daysInMonths,
                                                       List<JTextField> sickDaysInMonth) {
        List<Integer> remainingDays = new ArrayList<>();

        for (int i = 0; i < daysInMonths.size(); i++) {
            int daysInMonth = daysInMonths.get(i);
            int usedSickDays = 0;

            try {
                usedSickDays = Integer.parseInt(sickDaysInMonth.get(i).getText());
            } catch (NumberFormatException e) {
                usedSickDays = 0;
            }

            int remaining = daysInMonth - usedSickDays;
            if (remaining < 0) {
                remaining = 0;
            }
            remainingDays.add(remaining);
        }
        return remainingDays;
    }

    /**
     * Средняя заработная плата за месяц
     *
     * @param sumOfActualSalary     зарплата за месяц
     * @param remainingCalendarDays количество рабочих календарных дней
     * @return заработная плата за месяц
     */
    public static List<Double> calculateAverageSalary(List<JTextField> sumOfActualSalary,
                                                      List<Integer> remainingCalendarDays) {
        List<Double> averages = new ArrayList<>();

        for (int i = 0; i < sumOfActualSalary.size(); i++) {
            JTextField textField = sumOfActualSalary.get(i);
            String textFieldValue = textField.getText();

            if (textFieldValue == null || textFieldValue.isEmpty()) {
                textField.setText("0");
                textFieldValue = "0";
            }

            double salary = Double.parseDouble(textFieldValue);
            double remainingDays = remainingCalendarDays.get(i);
            double averageDailySalary = salary / remainingDays;
            averages.add(Math.round(averageDailySalary * 100.0) / 100.0);
        }

        return averages;
    }

    /**
     * Расчёт общего количества больничных дней
     *
     * @param sickDays больничные дни за месяц
     * @return общее количество больничных дней
     */
    public static int totalSickDays(List<JTextField> sickDays) {
        int sum = 0;
        for (JTextField textField : sickDays) {
            if (textField != null && !textField.getText().isEmpty()) {
                sum += Integer.parseInt(textField.getText());
            }
        }
        return sum;
    }

    /**
     * Расчёт общей заработной платы
     *
     * @param monthSalary зарплата за месяц
     * @return общая заработная плата
     */
    public static double calculateTotalSalary(List<JTextField> monthSalary) {
        double totalSalary = 0.0;
        for (JTextField salaryField : monthSalary) {
            String salaryString = salaryField.getText();
            if (salaryString == null || salaryString.trim().isEmpty()) {
                salaryString = "0";
            }
            double salary = Double.parseDouble(salaryString);
            totalSalary += salary;
        }
        return totalSalary;
    }


    /**
     * Расчёт средней заработной платы за 6 месяцев
     *
     * @param totalSalary                зарплата за 6 месяцев
     * @param totalRemainingCalendarDays количество рабочих календарных дней
     * @return Общая средняя заработная плата
     */
    public static double calculateTotalAverageSalary(double totalSalary, int totalRemainingCalendarDays) {
        double totalAverageSalary = totalSalary / totalRemainingCalendarDays;
        return Math.round(totalAverageSalary * 100.0) / 100.0;
    }

    /**
     * Расчёт зарплата за дни в размере 80% заработка
     *
     * @param averageSalary       средняя заработная плата
     * @param numberOfWorkingDays количество рабочих дней за 6 месяцев
     * @return зарплата за дни в размере 80% заработка
     */
    public static double calculate80PercentOfAverageSalary(double averageSalary, int numberOfWorkingDays) {
        double salary;
        if (numberOfWorkingDays <= 12) {
            salary = averageSalary * numberOfWorkingDays * 0.8;
        } else {
            salary = averageSalary * 12 * 0.8;
        }
        return Math.round(salary * 100.0) / 100.0;
    }

    /**
     * Расчёт зарплаты за дни в размере 100% заработка
     *
     * @param averageSalary       средняя заработная плата
     * @param numberOfWorkingDays количество рабочих дней за 6 месяцев
     * @return зарплата за дни в размере 100% заработка
     */
    public static double calculateFullSalary(double averageSalary, int numberOfWorkingDays) {
        if (numberOfWorkingDays <= 12) {
            return 0;
        } else {
            int daysOver12 = numberOfWorkingDays - 12;
            double fullSalary = daysOver12 * averageSalary;
            return Math.round(fullSalary * 100.0) / 100.0;
        }
    }
}
