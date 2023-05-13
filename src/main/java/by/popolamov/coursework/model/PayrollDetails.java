package by.popolamov.coursework.model;

import java.util.Date;

/**
 * @author Denis Popolamov
 */
public class PayrollDetails {
    Long payrollId;
    int totalSickDates;
    double totalSalary;
    int totalRemainingDays;
    double totalAverageSalary;
    double totalPayrollSum;
    String userSurName;
    String userName;
    String userPatronymic;
    Date startIllnessDate;
    Date endIllnessDate;
    String currentMonth;
    double eightyPercentSalary;
    double hundredPercentSalary;
    int illnessDays;
    int totalMonthDays;


    public PayrollDetails() {
    }

    public PayrollDetails(int totalSickDates, double totalSalary, int totalRemainingDays,
                          double totalAverageSalary,
                          double totalPayrollSum, String userSurName, String userName,
                          String userPatronymic, Date startIllnessDate, Date endIllnessDate,
                          String currentMonth, double eightyPercentSalary,
                          double hundredPercentSalary, int illnessDays, int totalMonthDays) {
        this.totalSickDates = totalSickDates;
        this.totalSalary = totalSalary;
        this.totalRemainingDays = totalRemainingDays;
        this.totalAverageSalary = totalAverageSalary;
        this.totalPayrollSum = totalPayrollSum;
        this.userSurName = userSurName;
        this.userName = userName;
        this.userPatronymic = userPatronymic;
        this.startIllnessDate = startIllnessDate;
        this.endIllnessDate = endIllnessDate;
        this.currentMonth = currentMonth;
        this.eightyPercentSalary = eightyPercentSalary;
        this.hundredPercentSalary = hundredPercentSalary;
        this.illnessDays = illnessDays;
        this.totalMonthDays = totalMonthDays;

    }

    public PayrollDetails(Long payrollId, int totalSickDates, double totalSalary,
                          int totalRemainingDays, double totalAverageSalary,
                          double totalPayrollSum, String userSurName, String userName,
                          String userPatronymic, Date startIllnessDate, Date endIllnessDate,
                          String currentMonth, double eightyPercentSalary,
                          double hundredPercentSalary, int illnessDays, int totalMonthDays) {
        this.payrollId = payrollId;
        this.totalSickDates = totalSickDates;
        this.totalSalary = totalSalary;
        this.totalRemainingDays = totalRemainingDays;
        this.totalAverageSalary = totalAverageSalary;
        this.totalPayrollSum = totalPayrollSum;
        this.userSurName = userSurName;
        this.userName = userName;
        this.userPatronymic = userPatronymic;
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

    public double getTotalAverageSalary() {
        return totalAverageSalary;
    }

    public void setTotalAverageSalary(double totalAverageSalary) {
        this.totalAverageSalary = totalAverageSalary;
    }

    public double getTotalPayrollSum() {
        return totalPayrollSum;
    }

    public void setTotalPayrollSum(double totalPayrollSum) {
        this.totalPayrollSum = totalPayrollSum;
    }

    public String getUserSurName() {
        return userSurName;
    }

    public void setUserSurName(String userSurName) {
        this.userSurName = userSurName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPatronymic() {
        return userPatronymic;
    }

    public void setUserPatronymic(String userPatronymic) {
        this.userPatronymic = userPatronymic;
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

}
