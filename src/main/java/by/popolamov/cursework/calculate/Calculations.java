package by.popolamov.cursework.calculate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Popolamov
 */
public class Calculations {
    public static List<Integer> calculateRemainingDays(List<Integer> daysInMonths, List<JTextField> sickDaysTextFields) {
        List<Integer> remainingDays = new ArrayList<>();

        for (int i = 0; i < daysInMonths.size(); i++) {
            int daysInMonth = daysInMonths.get(i);
            int usedSickDays = 0;

            try {
                usedSickDays = Integer.parseInt(sickDaysTextFields.get(i).getText());
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

    public static List<Double> calculateAverageSalary(List<JTextField> sumOfActualSalaryTextField, List<Integer> remainingCalendarDays) {
        List<Double> averages = new ArrayList<>();

        for (int i = 0; i < sumOfActualSalaryTextField.size(); i++) {
            JTextField textField = sumOfActualSalaryTextField.get(i);
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

    public static int sumSickDays(List<JTextField> sickDaysTextField) {
        int sum = 0;
        for (JTextField textField : sickDaysTextField) {
            if (textField != null && !textField.getText().isEmpty()) {
                sum += Integer.parseInt(textField.getText());
            }
        }
        return sum;
    }

    public static double calculateTotalSalary(List<JTextField> salaryFields) {
        double totalSalary = 0.0;
        for (JTextField salaryField : salaryFields) {
            String salaryString = salaryField.getText();
            if (salaryString == null || salaryString.trim().isEmpty()) {
                salaryString = "0";
            }
            double salary = Double.parseDouble(salaryString);
            totalSalary += salary;
        }
        return totalSalary;
    }

    public static double calculateTotalAverageSalary(List<Double> remainingAvarageSalary) {
        double totalAverageSalary = 0;
        for (double averageSalary : remainingAvarageSalary) {
            totalAverageSalary += averageSalary;
        }
        return Math.round(totalAverageSalary * 100.0) / 100.0;
    }

    public static double calculate80PercentOfAverageSalary(double averageSalary, int numberOfWorkingDays) {
        double salary;
        if (numberOfWorkingDays <= 12) {
            salary = averageSalary * numberOfWorkingDays * 0.8;
        } else {
            salary = averageSalary * 12 * 0.8;
        }
        return Math.round(salary * 100.0) / 100.0;
    }

    public static double calculateFullSalary(double averageSalary, int numberOfWorkingDays) {
        if (numberOfWorkingDays <= 12) {
            return 0;
        } else {
            int daysOver12 = numberOfWorkingDays - 12;
            double fullSalary = daysOver12 * averageSalary;
            return Math.round(fullSalary * 100.0) / 100.0;
        }
    }


//    public static List<Double> calculateAverageSalary(List<JTextField> sumOfActualSalaryTextField, List<Integer> remainingCalendarDays) {
//        List<Double> averages = new ArrayList<>();
//
//        for (int i = 0; i < sumOfActualSalaryTextField.size(); i++) {
//            double salary = Double.parseDouble(sumOfActualSalaryTextField.get(i).getText());
//            double remainingDays = remainingCalendarDays.get(i);
//            double averageDailySalary = salary / remainingDays;
//            averages.add(Math.round(averageDailySalary * 100.0) / 100.0);
//        }
//
//        return averages;
//    }


}
