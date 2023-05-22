package by.popolamov.coursework.model;

import java.util.List;

/**
 * Сущность средняя заработная плата
 *
 * @author Denis Popolamov
 */
public class AverageSalary {
    Long payrollId;
    List<Double> averageSalary;

    public AverageSalary() {
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
