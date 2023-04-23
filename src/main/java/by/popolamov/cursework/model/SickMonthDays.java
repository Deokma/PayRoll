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

    public SickMonthDays(List<Integer> sickMonthDays,
                         List<Integer> remainingCalendarDays, List<Integer> monthDays) {
        this.sickMonthDays = sickMonthDays;
        this.remainingCalendarDays = remainingCalendarDays;
        this.monthDays = monthDays;
    }

    public SickMonthDays(Long payrollId, List<Integer> sickMonthDays,
                         List<Integer> remainingCalendarDays, List<Integer> monthDays) {
        this.payrollId = payrollId;
        this.sickMonthDays = sickMonthDays;
        this.remainingCalendarDays = remainingCalendarDays;
        this.monthDays = monthDays;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
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
//        List<Integer> listRemainingDays = new ArrayList<>();
//        for (JLabel remainingDays : remainingCalendarDays){
//            listRemainingDays.add(Integer.parseInt(remainingDays.getText()));
//        }
        this.remainingCalendarDays = remainingCalendarDays;
    }

    public List<Integer> getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(List<Integer> monthDays) {
        this.monthDays = monthDays;
    }

}
