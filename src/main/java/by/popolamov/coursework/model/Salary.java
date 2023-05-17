package by.popolamov.coursework.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Заработная плата
 *
 * @author Denis Popolamov
 */
public class Salary {
    Long payrollId;
    List<Double> salary;

    public Salary() {

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
