package by.popolamov.cursework.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Popolamov
 */
public class SickMonthDays {
    Long payrollId;
    List<Integer> sickMonthDays;
    List<Integer> remainingCalendarDays;
    List<Integer> monthDays;

    public SickMonthDays() {

    }

    public List<Integer> getSickMonthDays() {
        return sickMonthDays;
    }

    public void setSickMonthDaysTextField(List<JTextField> listSickMonthDays) {
        List<Integer> sickMonthDays = new ArrayList<>();
        for (JTextField sickDays : listSickMonthDays) {
            sickMonthDays.add(Integer.parseInt(sickDays.getText()));
        }
        this.sickMonthDays = sickMonthDays;
    }

    public void setSickMonthDays(List<Integer> sickMonthDays) {
        this.sickMonthDays = sickMonthDays;
    }

    public List<Integer> getRemainingCalendarDays() {
        return remainingCalendarDays;
    }

    public void setRemainingCalendarDays(List<Integer> remainingCalendarDays) {
        this.remainingCalendarDays = remainingCalendarDays;
    }

    public List<Integer> getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(List<Integer> monthDays) {
        this.monthDays = monthDays;
    }

}
