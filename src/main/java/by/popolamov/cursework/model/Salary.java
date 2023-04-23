package by.popolamov.cursework.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Popolamov
 */
public class Salary {
    Long payrollId;
    List<Double> salary;

    public Salary() {

    }
    public Salary( List<Double> salary) {
        this.salary = salary;
    }
    public Salary(Long payrollId, List<Double> salary) {
        this.payrollId = payrollId;
        this.salary = salary;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public List<Double> getSalary() {
        return salary;
    }
    public void setSalaryTextField(List<JTextField> jtfSalary) {
        List<Double> salaries = new ArrayList<>();
        for (JTextField salary : jtfSalary) {
            salaries.add(Double.parseDouble(salary.getText()));
        }
        this.salary = salaries;
    }
    public void setSalary(List<Double> salary) {
        this.salary = salary;
    }

}
