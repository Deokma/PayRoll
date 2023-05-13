package by.popolamov.coursework.model;

import java.util.List;

/**
 * @author Denis Popolamov
 */
public class PayrollMonths {
    Long payrollId;
    List<String> month;

    public PayrollMonths() {
    }

    public List<String> getMonth() {
        return month;
    }

    public void setMonth(List<String> month) {
        this.month = month;
    }
}
