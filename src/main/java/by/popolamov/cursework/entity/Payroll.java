package by.popolamov.cursework.entity;

import java.util.Date;

/**
 * Сущность Payroll
 *
 * @author Denis Popolamov
 */
public class Payroll {
    Long payrollId;
    int totalSickDates;
    double totalSalary;
    int totalRemainingDays;
    double averageSalary;
    double totalSumOfPayroll;
    String userName;
    String userSurname;
    Date startIllnessDate;
    Date endIllnessDate;
    String currentMonth;
    double eightyPercentSalary;
    double hundredPercentSalary;
    int illnessDays;
    int totalMonthDays;

    public Payroll(Long payrollId, int totalSickDates, double totalSalary,
                   int totalRemainingDays, double averageSalary, double totalSumOfPayroll,
                   String userName, String userSurname, Date startIllnessDate,
                   Date endIllnessDate, String currentMonth, double eightyPercentSalary,
                   double hundredPercentSalary, int illnessDays, int totalMonthDays) {
        this.payrollId = payrollId;
        this.totalSickDates = totalSickDates;
        this.totalSalary = totalSalary;
        this.totalRemainingDays = totalRemainingDays;
        this.averageSalary = averageSalary;
        this.totalSumOfPayroll = totalSumOfPayroll;
        this.userName = userName;
        this.userSurname = userSurname;
        this.startIllnessDate = startIllnessDate;
        this.endIllnessDate = endIllnessDate;
        this.currentMonth = currentMonth;
        this.eightyPercentSalary = eightyPercentSalary;
        this.hundredPercentSalary = hundredPercentSalary;
        this.illnessDays = illnessDays;
        this.totalMonthDays = totalMonthDays;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public int getTotalSickDates() {
        return totalSickDates;
    }

    public void setTotalSickDates(int totalSickDates) {
        this.totalSickDates = totalSickDates;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public int getTotalRemainingDays() {
        return totalRemainingDays;
    }

    public void setTotalRemainingDays(int totalRemainingDays) {
        this.totalRemainingDays = totalRemainingDays;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public double getTotalSumOfPayroll() {
        return totalSumOfPayroll;
    }

    public void setTotalSumOfPayroll(double totalSumOfPayroll) {
        this.totalSumOfPayroll = totalSumOfPayroll;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Date getStartIllnessDate() {
        return startIllnessDate;
    }

    public void setStartIllnessDate(Date startIllnessDate) {
        this.startIllnessDate = startIllnessDate;
    }

    public Date getEndIllnessDate() {
        return endIllnessDate;
    }

    public void setEndIllnessDate(Date endIllnessDate) {
        this.endIllnessDate = endIllnessDate;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public double getEightyPercentSalary() {
        return eightyPercentSalary;
    }

    public void setEightyPercentSalary(double eightyPercentSalary) {
        this.eightyPercentSalary = eightyPercentSalary;
    }

    public double getHundredPercentSalary() {
        return hundredPercentSalary;
    }

    public void setHundredPercentSalary(double hundredPercentSalary) {
        this.hundredPercentSalary = hundredPercentSalary;
    }

    public int getIllnessDays() {
        return illnessDays;
    }

    public void setIllnessDays(int illnessDays) {
        this.illnessDays = illnessDays;
    }

    public int getTotalMonthDays() {
        return totalMonthDays;
    }

    public void setTotalMonthDays(int totalMonthDays) {
        this.totalMonthDays = totalMonthDays;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", totalSickDates=" + totalSickDates +
                ", totalSalary=" + totalSalary +
                ", totalRemainingDays=" + totalRemainingDays +
                ", averageSalary=" + averageSalary +
                ", totalSumOfPayroll=" + totalSumOfPayroll +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", startIllnessDate=" + startIllnessDate +
                ", endIllnessDate=" + endIllnessDate +
                ", currentMonth='" + currentMonth + '\'' +
                ", eightyPercentSalary=" + eightyPercentSalary +
                ", hundredPercentSalary=" + hundredPercentSalary +
                ", illnessDays=" + illnessDays +
                ", totalMonthDays=" + totalMonthDays +
                '}';
    }
}
