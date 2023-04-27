package by.popolamov.cursework.exceptions;

import javax.swing.*;
import java.util.List;

/**
 * @author Denis Popolamov
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }

    public static boolean isSalaryValid(List<JTextField> salaryFields, List<Integer> remainingDays) throws InvalidInputException {
        for (int i = 0; i < salaryFields.size(); i++) {
            JTextField salaryField = salaryFields.get(i);
            String textFieldValue = salaryField.getText();
            if (textFieldValue == null || textFieldValue.isEmpty()) {
                salaryField.setText("0");
                textFieldValue = "0";
            }
            double salary = Double.parseDouble(textFieldValue);
            if (salary > remainingDays.get(i)) {
                throw new InvalidInputException("The salary in field " + (i + 1) + " is greater than the remaining days in the month");
            }
        }
        return true;
    }
}
