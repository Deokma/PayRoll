package by.popolamov.cursework.model;

import java.util.List;

/**
 * @author Denis Popolamov
 */
public class AverageSalary {
    Long payrollId;
    List<Double> averageSalary;

    public AverageSalary() {
    }
    public AverageSalary( List<Double> averageSalary) {
        this.averageSalary = averageSalary;
    }
    public AverageSalary(Long payrollId, List<Double> averageSalary) {
        this.payrollId = payrollId;
        this.averageSalary = averageSalary;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public List<Double> getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(List<Double> averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return "AverageSalary{" +
                "payrollId=" + payrollId +
                ", averageSalary=" + averageSalary +
                '}';
    }
}
