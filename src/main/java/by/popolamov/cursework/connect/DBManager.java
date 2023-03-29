package by.popolamov.cursework.connect;

/**
 * @author Denis Popolamov
 */

import by.popolamov.cursework.gui.MainWindow;
import by.popolamov.cursework.gui.PayrollDetailsDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public DefaultTableModel getAllPayrolls() {
        String[] columns = {"№", "Фамилия", "Имя", "Отчество",
                "Нач. дата болезни", "Кон. дата болезни", "Сумма выплаты", ""};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM payroll_details");
            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("payroll_id");
                row[1] = rs.getString("user_surname");
                row[2] = rs.getString("user_name");
                row[3] = rs.getString("user_patronimic");
                row[4] = rs.getDate("start_illness_date");
                row[5] = rs.getDate("end_illness_date");
                row[6] = rs.getDouble("total_sum_of_payroll");
                model.addRow(row);
            }
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    Метод для получения строки из таблицы payroll_details по payrollId
    public void getPayrollDetails(int payrollId) {
        try {
            String query = "SELECT * FROM payroll_details WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            MainWindow mainWindow = new MainWindow();
            PayrollDetailsDialog dialog = new PayrollDetailsDialog(mainWindow, null);
            if (rs.next()) {
                // Создаем экземпляр нового класса PayrollDetailsDialog

                // Вызываем метод setPayrollDetails с передачей данных из результата запроса

                //monthlyAverageSalary.add(rs.getDouble("average_salary"));
                //months.add(rs.getString("month"));
                //salarys.add(rs.getDouble("salary"));
                //sickMonthDays.add(rs.getInt("sick_month_days"));
                //remainingCalendarDays.add(rs.getInt("remaining_calendar_days"));

                dialog.setPayrollDetails(new Object[]{
                        rs.getLong("payroll_id"),
                        rs.getString("user_surname"),
                        rs.getString("user_name"),
                        rs.getString("user_patronimic"),
                        rs.getDate("start_illness_date"),
                        rs.getDate("end_illness_date"),
                        rs.getString("total_sick_dates"),
                        rs.getString("total_salary"),
                        rs.getString("total_remaining_days"),
                        rs.getDouble("total_average_salary"),
                        rs.getDouble("total_sum_of_payroll"),
                        rs.getString("current_month"),
                        rs.getDouble("eighty_percent_salary"),
                        rs.getDouble("hundred_percent_salary")
                }); //monthlyAverageSalary, months, salarys, sickMonthDays, remainingCalendarDays);

                // Отображаем диалоговое окно
                dialog.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Double> getAverageSalaryByPayrollId(long payrollId) {
        List<Double> averageSalaryList = new ArrayList<>();
        try {
            String query = "SELECT average_salary FROM average_salary WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double averageSalary = rs.getDouble("average_salary");
                averageSalaryList.add(averageSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageSalaryList;
    }

    public List<String> getPayrollMonthsByPayrollId(long payrollId) {
        List<String> monthList = new ArrayList<>();
        try {
            String query = "SELECT month FROM payroll_months WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");
                monthList.add(month);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthList;
    }

    public List<Double> getSalaryByPayrollId(long payrollId) {
        List<Double> salaryList = new ArrayList<>();
        try {
            String query = "SELECT salary FROM salary WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double salary = rs.getDouble("salary");
                salaryList.add(salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaryList;
    }

    public List<Integer> getSickMonthDaysByPayrollId(long payrollId) {
        List<Integer> sickMonthDaysList = new ArrayList<>();
        try {
            String query = "SELECT sick_month_days FROM sick_month_days WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer sickMonthDays = rs.getInt("sick_month_days");
                sickMonthDaysList.add(sickMonthDays);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sickMonthDaysList;
    }

    public List<Integer> getRemainingDaysByPayrollId(long payrollId) {
        List<Integer> remainingDaysList = new ArrayList<>();
        try {
            String query = "SELECT remaining_calendar_days FROM sick_month_days WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer remainingDays = rs.getInt("remaining_calendar_days");
                remainingDaysList.add(remainingDays);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return remainingDaysList;
    }


    public void saveAverageSalary(List<Double> AverageSalary) {
        int payrollId = 0;
        try {
            // Получаем payroll_id из таблицы payroll_details
            String payrollQuery = "SELECT payroll_id FROM payroll_details ORDER BY payroll_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(payrollQuery);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payrollId = rs.getInt("payroll_id");
            }

            // Сохраняем список средних зарплат в таблицу average_salary
            String insertQuery = "INSERT INTO average_salary (payroll_id, average_salary) VALUES (?, ?)";
            stmt = conn.prepareStatement(insertQuery);

            for (Double salary : AverageSalary) {
                stmt.setInt(1, payrollId);
                stmt.setDouble(2, salary);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePayrollMonths(List<String> pastMonths) {
        int payrollId = 0;
        try {
            // Получаем payroll_id из таблицы payroll_details
            String payrollQuery = "SELECT payroll_id FROM payroll_details ORDER BY payroll_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(payrollQuery);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payrollId = rs.getInt("payroll_id");
            }

            // Сохраняем список средних зарплат в таблицу average_salary
            String insertQuery = "INSERT INTO payroll_months (payroll_id, month) VALUES (?, ?)";
            stmt = conn.prepareStatement(insertQuery);

            for (String month : pastMonths) {
                stmt.setInt(1, payrollId);
                stmt.setString(2, month);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePayrollSalary(List<JTextField> txtSumOfActualSalary) {
        int payrollId = 0;
        try {
            // Получаем payroll_id из таблицы payroll_details
            String payrollQuery = "SELECT payroll_id FROM payroll_details ORDER BY payroll_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(payrollQuery);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payrollId = rs.getInt("payroll_id");
            }

            // Сохраняем список средних зарплат в таблицу average_salary
            String insertQuery = "INSERT INTO salary (payroll_id, salary) VALUES (?, ?)";
            stmt = conn.prepareStatement(insertQuery);

            for (JTextField salary : txtSumOfActualSalary) {
                stmt.setInt(1, payrollId);
                stmt.setDouble(2, Double.parseDouble(salary.getText()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePayrollWorkDays(List<Integer> daysInMonths, List<JLabel> remainingCalendarDays) {
        int payrollId = 0;
        try {
            // Получаем payroll_id из таблицы payroll_details
            String payrollQuery = "SELECT payroll_id FROM payroll_details ORDER BY payroll_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(payrollQuery);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payrollId = rs.getInt("payroll_id");
            }

            // Сохраняем список средних зарплат в таблицу average_salary
            String insertQuery = "INSERT INTO sick_month_days (payroll_id, sick_month_days, remaining_calendar_days) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(insertQuery);

            for (int i = 0; i < daysInMonths.size(); i++) {
                Integer days = daysInMonths.get(i);
                JLabel label = remainingCalendarDays.get(i);
                Integer remainingDays = Integer.parseInt(label.getText());

                stmt.setInt(1, payrollId);
                stmt.setInt(2, days);
                stmt.setInt(3, remainingDays);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPayrollDetails(int totalSickDays, double totalSalary,
                                  int totalRemainingDays, double totalAverageSalary, double totalPayrollSum,
                                  String userSurname, String userName, String userPatronimic,
                                  Date startIllnessDate, Date endIllnessDate, String currentMonth,
                                  double eightyPercentSalary, double hundredPercentSalary) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO payroll_details (total_sick_dates, total_salary," +
                    " total_remaining_days, total_average_salary, total_sum_of_payroll," +
                    "user_surname,user_name,user_patronimic," +
                    "start_illness_date,end_illness_date, current_month,eighty_percent_salary," +
                    "hundred_percent_salary) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, totalSickDays);
            ps.setDouble(2, totalSalary);
            ps.setInt(3, totalRemainingDays);
            ps.setDouble(4, totalAverageSalary);
            ps.setDouble(5, totalPayrollSum);
            ps.setString(6, userSurname);
            ps.setString(7, userName);
            ps.setString(8, userPatronimic);
            ps.setDate(9, startIllnessDate);
            ps.setDate(10, endIllnessDate);
            ps.setString(11, currentMonth);
            ps.setDouble(12, eightyPercentSalary);
            ps.setDouble(13, hundredPercentSalary);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePayroll(int userId) {
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
