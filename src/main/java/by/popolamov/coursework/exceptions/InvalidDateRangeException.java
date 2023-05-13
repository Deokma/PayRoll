package by.popolamov.coursework.exceptions;

/**
 * Исключение некорректной даты
 * @author Denis Popolamov
 */
public class InvalidDateRangeException extends Exception {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
