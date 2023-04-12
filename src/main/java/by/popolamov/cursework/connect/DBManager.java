package by.popolamov.cursework.connect;

/**
 * @author Denis Popolamov
 */

import by.popolamov.cursework.gui.windows.MainWindow;
import by.popolamov.cursework.gui.dialogs.PayrollDetailsDialog;

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

    /**
     * Конструктор класса для создания подключения к базе данных
     */
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

    /**
     * Метод для получения списка всех выплат
     *
     * @return все выплаты
     */
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

    /**
     * Метод для получения всех данных о выплате
     *
     * @param payrollId id выплаты
     */
    public void getPayrollDetails(int payrollId) {
        try {
            String query = "SELECT * FROM payroll_details WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            MainWindow mainWindow = new MainWindow();
            PayrollDetailsDialog dialog = new PayrollDetailsDialog(mainWindow, null);
            if (rs.next()) {
                // Вызываем метод setPayrollDetails с передачей данных из результата запроса
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
                        rs.getDouble("hundred_percent_salary"),
                        rs.getInt("illness_days"),
                        rs.getInt("total_month_days")
                });

                // Отображаем диалоговое окно
                dialog.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения средней заработной платы по payrollId
     *
     * @param payrollId id выплаты
     * @return список средней заработной платы за 6 месяцев
     */
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

    /**
     * Метод для получения списка 6 месяцев прошлых месяцев от выплаты по payrollId
     *
     * @param payrollId id выплаты
     * @return список 6 месяцев
     */
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

    /**
     * Метод для получения списка зарплат за прошлые 6 месяцев по payrollId
     *
     * @param payrollId id выплаты
     * @return список зарплат за прошлые 6 месяцев
     */
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

    /**
     * Метод для получения списка из количества больничных дней за 6 месяцев
     *
     * @param payrollId id выплаты
     * @return список из количества больничных дней за 6 месяцев
     */
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

    /**
     * Метод для получения списка дней 6 месяцев
     *
     * @param payrollId id выплаты
     * @return количество дней за 6 месяцев
     */
    public List<Integer> getMonthDaysByPayrollId(long payrollId) {
        List<Integer> sickMonthDaysList = new ArrayList<>();
        try {
            String query = "SELECT month_days FROM sick_month_days WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer sickMonthDays = rs.getInt("month_days");
                sickMonthDaysList.add(sickMonthDays);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sickMonthDaysList;
    }

    /**
     * Метод для получения оставшихся дней, от разности дней в месяце и больничных дней
     *
     * @param payrollId id выплаты
     * @return оставшиеся дни
     */
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


    /**
     * Метод для сохранения списка средней заработной платы за 6 месяцев
     *
     * @param AverageSalary средняя заработная плата
     */
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

    /**
     * Метод списка месяцев заработной платы за 6 месяцев
     *
     * @param pastMonths месяцы заработной платы
     */
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

    /**
     * Метод для сохранения списка заработной платы за 6 месяцев
     *
     * @param txtSumOfActualSalary заработная плата
     */
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

    /**
     * Метод для сохранения списка рабочих дней
     *
     * @param sickMonthDays         список больничных дней
     * @param remainingCalendarDays список оставшихся календарных дней
     * @param daysInMonths          список дней в месяцах
     */
    public void savePayrollWorkDays(List<JTextField> sickMonthDays, List<JLabel> remainingCalendarDays, List<Integer> daysInMonths) {
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
            String insertQuery = "INSERT INTO sick_month_days (payroll_id, sick_month_days, remaining_calendar_days,month_days) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertQuery);

            for (int i = 0; i < daysInMonths.size(); i++) {
                Integer days = daysInMonths.get(i);
                JLabel lblDays = remainingCalendarDays.get(i);
                Integer remainingDays = Integer.parseInt(lblDays.getText());
                JTextField lblSickDays = sickMonthDays.get(i);
                Integer sickDays = Integer.parseInt(lblSickDays.getText());

                stmt.setInt(1, payrollId);
                stmt.setInt(2, sickDays);
                stmt.setInt(3, remainingDays);
                stmt.setInt(4, days);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для записи выписки
     *
     * @param totalMonthDays Общее количество рабочих дней за 6 месяцев
     * @param totalSickDays Общее количество дней болезни
     * @param totalSalary Общая сумма зарплаты
     * @param totalRemainingDays Общее количество рабочих дней
     * @param totalAverageSalary Общая средняя заработная плата
     * @param totalPayrollSum Общая сумма выплаты
     * @param userSurname Фамилия
     * @param userName Имя
     * @param userPatronymic Отчество
     * @param startIllnessDate Начальная дата болезни
     * @param endIllnessDate Конечная дата болезни
     * @param currentMonth Месяц болезни
     * @param eightyPercentSalary Расчёт 80% выплаты
     * @param hundredPercentSalary Расчёт 100% выплаты
     * @param illnessDays Количество дней болезни
     */
    public void addPayrollDetails(int totalMonthDays, int totalSickDays, double totalSalary,
                                  int totalRemainingDays, double totalAverageSalary, double totalPayrollSum,
                                  String userSurname, String userName, String userPatronymic,
                                  Date startIllnessDate, Date endIllnessDate, String currentMonth,
                                  double eightyPercentSalary, double hundredPercentSalary, int illnessDays) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO payroll_details (total_month_days,total_sick_dates, total_salary," +
                    " total_remaining_days, total_average_salary, total_sum_of_payroll," +
                    "user_surname,user_name,user_patronimic," +
                    "start_illness_date,end_illness_date, current_month,eighty_percent_salary," +
                    "hundred_percent_salary, illness_days) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
            ps.setInt(1, totalMonthDays);
            ps.setInt(2, totalSickDays);
            ps.setDouble(3, totalSalary);
            ps.setInt(4, totalRemainingDays);
            ps.setDouble(5, totalAverageSalary);
            ps.setDouble(6, totalPayrollSum);
            ps.setString(7, userSurname);
            ps.setString(8, userName);
            ps.setString(9, userPatronymic);
            ps.setDate(10, startIllnessDate);
            ps.setDate(11, endIllnessDate);
            ps.setString(12, currentMonth);
            ps.setDouble(13, eightyPercentSalary);
            ps.setDouble(14, hundredPercentSalary);
            ps.setInt(15, illnessDays);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для удаления выплаты
     *
     * @param payrollId id выплаты
     */
    public void deletePayrollData(long payrollId) throws SQLException {
        // Удаление строк из таблицы salary
        PreparedStatement salaryStmt = conn.prepareStatement("DELETE FROM salary WHERE payroll_id = ?");
        salaryStmt.setLong(1, payrollId);
        salaryStmt.executeUpdate();

        // Удаление строк из таблицы average_salary
        PreparedStatement averageSalaryStmt = conn.prepareStatement("DELETE FROM average_salary WHERE payroll_id = ?");
        averageSalaryStmt.setLong(1, payrollId);
        averageSalaryStmt.executeUpdate();

        // Удаление строк из таблицы payroll_months
        PreparedStatement payrollMonthsStmt = conn.prepareStatement("DELETE FROM payroll_months WHERE payroll_id = ?");
        payrollMonthsStmt.setLong(1, payrollId);
        payrollMonthsStmt.executeUpdate();

        // Удаление строк из таблицы sick_month_days
        PreparedStatement sickMonthDaysStmt = conn.prepareStatement("DELETE FROM sick_month_days WHERE payroll_id = ?");
        sickMonthDaysStmt.setLong(1, payrollId);
        sickMonthDaysStmt.executeUpdate();

        // Удаление строк из таблицы payroll_details
        PreparedStatement payrollDetailsStmt = conn.prepareStatement("DELETE FROM payroll_details WHERE payroll_id = ?");
        payrollDetailsStmt.setLong(1, payrollId);
        payrollDetailsStmt.executeUpdate();
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
