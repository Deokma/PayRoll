package by.popolamov.cursework.connect;

/**
 * @author Denis Popolamov
 */

import java.sql.*;
import java.time.LocalDate;

/**
 * @author Denis Popolamov
 */
public class DBManager {

    private Connection conn;

    public DBManager() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/payroll";
            String user = "postgres";
            String password = "qwerty";
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int addEmployee(String surname, String name, String patronimic,
                           Date startIllnesDate, Date endIllnesDate, Double paymentSum) {
        int generatedId = -1;
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO employees (user_surname, user_name," +
                    " user_patronimic, start_illness_date, end_illness_date, payment_sum) VALUES (?, ?, ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setString(3, patronimic);
            ps.setDate(4, startIllnesDate);
            ps.setDate(5, endIllnesDate);
            ps.setDouble(6, paymentSum);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public ResultSet getAllEmployees() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPayrollDetails(int payrollId, int totalSickDays, double totalSalary, int totalRemainingDays, double totalAverageSalary, double totalPayrollSum) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO payroll_details (payroll_id, total_sick_dates, total_salary, total_remaining_days, total_average_salary, total_sum_of_payroll) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, payrollId);
            ps.setInt(2, totalSickDays);
            ps.setDouble(3, totalSalary);
            ps.setInt(4, totalRemainingDays);
            ps.setDouble(5, totalAverageSalary);
            ps.setDouble(6, totalPayrollSum);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int userId) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM employees WHERE user_id = ?");
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPayrollDetails() {

    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
