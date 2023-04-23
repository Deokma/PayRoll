package by.popolamov.cursework.model;

import java.util.List;

/**
 * @author Denis Popolamov
 */
public class PayrollMonths {
    Long payrollId;
    List<String> month;

    public PayrollMonths() {
    }
    public PayrollMonths( List<String> month) {
        this.month = month;
    }
    public PayrollMonths(Long payrollId, List<String> month) {
        this.payrollId = payrollId;
        this.month = month;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public List<String> getMonth() {
        return month;
    }

    public void setMonth(List<String> month) {
        this.month = month;
    }
}
