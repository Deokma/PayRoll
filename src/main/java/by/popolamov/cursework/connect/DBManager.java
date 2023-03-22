package by.popolamov.cursework.connect;

/**
 * @author Denis Popolamov
 */

import by.popolamov.cursework.gui.PayrollDetailsDialog;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

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

    public DefaultTableModel getAllEmployees() {
        String[] columns = {"№", "Фамилия", "Имя", "Отчество",
                "Нач. дата болезни", "Кон. дата болезни", "Сумма выплаты", ""};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("payroll_id");
                row[1] = rs.getString("user_surname");
                row[2] = rs.getString("user_name");
                row[3] = rs.getString("user_patronimic");
                row[4] = rs.getDate("start_illness_date");
                row[5] = rs.getDate("end_illness_date");
                row[6] = rs.getDouble("payment_sum");
                model.addRow(row);
            }
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAdditionalInfo(int employeeId) {
        try {
            String query = "SELECT * FROM employees WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.getString("user_surname");
                rs.getString("user_name");
                rs.getString("user_patronimic");
                rs.getString("start_illness_date");
                rs.getString("end_illness_date");
                //String firstName = rs.getString("first_name");
                //String lastName = rs.getString("last_name");
                // Устанавливаем полученную информацию на окне
                //JLabel firstNameLabel = new JLabel("Имя: " + firstName);
                //JLabel lastNameLabel = new JLabel("Фамилия: " + lastName);
                //getContentPane().add(firstNameLabel, BorderLayout.NORTH);
                //getContentPane().add(lastNameLabel, BorderLayout.SOUTH);
                //pack();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


 //    Метод для получения строки из таблицы payroll_details по payrollId
    public void getPayrollDetails(int payrollId) {
        try {
          //  String query = "SELECT * FROM payroll_details WHERE payroll_id = ?";
            String query = "SELECT * FROM employees e " +
                    "JOIN payroll_details pd ON e.payroll_id = pd.payroll_id " +
                    "WHERE pd.payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, payrollId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Создаем экземпляр нового класса PayrollDetailsDialog
                PayrollDetailsDialog dialog = new PayrollDetailsDialog(null, null);
                // Вызываем метод setPayrollDetails с передачей данных из результата запроса
                dialog.setPayrollDetails(new Object[]{
                        rs.getString("user_surname"),
                        rs.getString("user_name"),
                        rs.getString("user_patronimic"),
                        rs.getDate("start_illness_date"),
                        rs.getDate("end_illness_date"),
                        rs.getString("total_sick_dates"),
                        rs.getString("total_salary"),
                        rs.getString("total_remaining_days"),
                        rs.getDouble("total_average_salary"),
                        rs.getDouble("total_sum_of_payroll")
                });

                // Отображаем диалоговое окно
                dialog.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public void getPayrollDetails(int payrollId) {
//        try {
//            String query = "SELECT * FROM payroll_details WHERE payroll_id = ?";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setInt(1, payrollId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                // Получаем данные из таблицы employee для сотрудника, связанного с выплатой
//                String employeeQuery = "SELECT * FROM employees WHERE payroll_id = ?";
//                PreparedStatement employeeStmt = conn.prepareStatement(employeeQuery);
//                employeeStmt.setInt(1, rs.getInt("payroll_id"));
//                ResultSet employeeRs = employeeStmt.executeQuery();
//                String employeeName = employeeRs.getString("user_name");
//                String employeePosition = employeeRs.getString("user_surname");
//                // Создаем экземпляр нового класса PayrollDetailsDialog
//                PayrollDetailsDialog dialog = new PayrollDetailsDialog(null, null);
//
//                // Вызываем метод setPayrollDetails с передачей данных из результата запроса и данных из таблицы employee
//                dialog.setPayrollDetails(new Object[]{rs.getString("total_sick_dates"),
//                        rs.getString("total_salary"),
//                        rs.getString("total_remaining_days"),
//                        rs.getDouble("total_average_salary"),
//                        rs.getDouble("total_sum_of_payroll"),
//                        employeeName,
//                        employeePosition});
//
//                // Отображаем диалоговое окно
//                dialog.setVisible(true);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


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
