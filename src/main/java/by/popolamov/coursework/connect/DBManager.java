package by.popolamov.coursework.connect;

import by.popolamov.coursework.gui.dialogs.PayrollDetailsDialog;
import by.popolamov.coursework.gui.windows.MainWindow;
import by.popolamov.coursework.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс для взаимодействия с базой данных
 *
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
            Properties props = new Properties();
            InputStream input = new FileInputStream("src/main/resources/database.properties");
            //InputStream input = ClassLoader.getSystemResourceAsStream("resources/database.properties");
            props.load(input);
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Класс не обнаружен: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Метод для получения списка всех выплат
     *
     * @return все выплаты
     */
    public DefaultTableModel getAllPayrolls() {
        String[] columns = {"№", "Фамилия", "Имя", "Отчество",
                "Нач.дата болезни", "Кон.дата болезни", "Сумма выплаты", ""};
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


            if (rs.next()) {
                // Создаем объект PayrollDetails из данных результата запроса
                PayrollDetails payrollDetails = new PayrollDetails(
                        rs.getLong("payroll_id"),
                        rs.getInt("total_sick_dates"),
                        rs.getDouble("total_salary"),
                        rs.getInt("total_remaining_days"),
                        rs.getDouble("total_average_salary"),
                        rs.getDouble("total_sum_of_payroll"),
                        rs.getString("user_surname"),
                        rs.getString("user_name"),
                        rs.getString("user_patronimic"),
                        rs.getDate("start_illness_date"),
                        rs.getDate("end_illness_date"),
                        rs.getString("current_month"),
                        rs.getDouble("eighty_percent_salary"),
                        rs.getDouble("hundred_percent_salary"),
                        rs.getInt("illness_days"),
                        rs.getInt("total_month_days")
                );
                AverageSalary averageSalary = getAverageSalaryByPayrollId(payrollId);
                PayrollMonths payrollMonths = getPayrollMonthsByPayrollId(payrollId);
                Salary salary = getSalaryByPayrollId(payrollId);
                SickMonthDays sickMonthDays = getSickMonthDaysByPayrollId(payrollId);
                PayrollDetailsDialog dialog = new PayrollDetailsDialog(mainWindow, payrollDetails,
                        averageSalary, payrollMonths, salary, sickMonthDays);
                // Отображаем диалоговое окно
                dialog.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка выполнения программы: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Метод для получения средней заработной платы по payrollId
     *
     * @param payrollId id выплаты
     * @return список средней заработной платы за 6 месяцев
     */
    public AverageSalary getAverageSalaryByPayrollId(long payrollId) {
        List<Double> averageSalaryList = new ArrayList<>();
        AverageSalary averageSalary = new AverageSalary();
        try {
            String query = "SELECT average_salary FROM average_salary WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double dAverageSalary = rs.getDouble("average_salary");
                averageSalaryList.add(dAverageSalary);
                averageSalary.setAverageSalary(averageSalaryList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageSalary;
    }

    /**
     * Метод для получения списка 6 месяцев прошлых месяцев от выплаты по payrollId
     *
     * @param payrollId id выплаты
     * @return список 6 месяцев
     */
    public PayrollMonths getPayrollMonthsByPayrollId(long payrollId) {
        List<String> monthList = new ArrayList<>();
        PayrollMonths payrollMonths = new PayrollMonths();
        try {
            String query = "SELECT month FROM payroll_months WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");
                monthList.add(month);
                payrollMonths.setMonth(monthList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payrollMonths;
    }

    /**
     * Метод для получения списка зарплат за прошлые 6 месяцев по payrollId
     *
     * @param payrollId id выплаты
     * @return список зарплат за прошлые 6 месяцев
     */
    public Salary getSalaryByPayrollId(long payrollId) {
        List<Double> salaryList = new ArrayList<>();
        Salary salary = new Salary();
        try {
            String query = "SELECT salary FROM salary WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double dSalary = rs.getDouble("salary");
                salaryList.add(dSalary);
                salary.setSalary(salaryList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salary;
    }

    /**
     * Метод для получения списка из количества больничных дней за 6 месяцев
     *
     * @param payrollId id выплаты
     * @return список из количества больничных дней за 6 месяцев
     */
    public SickMonthDays getSickMonthDaysByPayrollId(long payrollId) {
        SickMonthDays sickMonthDays = new SickMonthDays();
        List<Integer> sickMonthDaysList = new ArrayList<>();
        List<Integer> remainingCalendarDaysList = new ArrayList<>();
        List<Integer> monthDaysList = new ArrayList<>();
        try {
            String query = "SELECT sick_month_days, remaining_calendar_days,month_days" +
                    " FROM sick_month_days WHERE payroll_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer sickMonthDaysInt = rs.getInt("sick_month_days");
                Integer remainingCalendarDaysInt = rs.getInt("remaining_calendar_days");
                Integer monthDaysInt = rs.getInt("month_days");
                sickMonthDaysList.add(sickMonthDaysInt);
                remainingCalendarDaysList.add(remainingCalendarDaysInt);
                monthDaysList.add(monthDaysInt);
                sickMonthDays.setSickMonthDays(sickMonthDaysList);
                sickMonthDays.setRemainingCalendarDays(remainingCalendarDaysList);
                sickMonthDays.setMonthDays(monthDaysList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sickMonthDays;
    }

    /**
     * Метод для сохранения списка средней заработной платы за 6 месяцев
     *
     * @param averageSalary средняя заработная плата
     */
    public void saveAverageSalary(AverageSalary averageSalary) {
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

            for (Double salary : averageSalary.getAverageSalary()) {
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
     * @param payrollMonths месяцы заработной платы
     */
    public void savePayrollMonths(PayrollMonths payrollMonths) {
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

            for (String month : payrollMonths.getMonth()) {
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
     * @param salary зарплата
     */
    public void savePayrollSalary(Salary salary) {
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

            for (double salarys : salary.getSalary()) {
                stmt.setInt(1, payrollId);
                stmt.setDouble(2, salarys);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для сохранения списка рабочих дней
     *
     * @param sickMonthDays рабочие дни
     */
    public void savePayrollSickMonthDays(SickMonthDays sickMonthDays) {
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
            String insertQuery = "INSERT INTO sick_month_days " +
                    "(payroll_id, sick_month_days, remaining_calendar_days,month_days) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertQuery);

            for (int i = 0; i < sickMonthDays.getSickMonthDays().size(); i++) {
                Integer days = sickMonthDays.getSickMonthDays().get(i);
                Integer remainingDays = sickMonthDays.getRemainingCalendarDays().get(i);
                Integer sickDays = sickMonthDays.getMonthDays().get(i);

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
     * Метод для сохранения деталей о расчётах выплаты
     *
     * @param payrollDetails детали выплаты
     */
    public void addPayrollDetails(PayrollDetails payrollDetails) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO payroll_details " +
                    "(total_month_days,total_sick_dates, total_salary," +
                    " total_remaining_days, total_average_salary, total_sum_of_payroll," +
                    "user_surname,user_name,user_patronimic," +
                    "start_illness_date,end_illness_date, current_month,eighty_percent_salary," +
                    "hundred_percent_salary, illness_days) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
            ps.setInt(1, payrollDetails.getTotalMonthDays());
            ps.setInt(2, payrollDetails.getTotalSickDates());
            ps.setDouble(3, payrollDetails.getTotalSalary());
            ps.setInt(4, payrollDetails.getTotalRemainingDays());
            ps.setDouble(5, payrollDetails.getTotalAverageSalary());
            ps.setDouble(6, payrollDetails.getTotalPayrollSum());
            ps.setString(7, payrollDetails.getUserSurName());
            ps.setString(8, payrollDetails.getUserName());
            ps.setString(9, payrollDetails.getUserPatronymic());
            ps.setDate(10, (Date) payrollDetails.getStartIllnessDate());
            ps.setDate(11, (Date) payrollDetails.getEndIllnessDate());
            ps.setString(12, payrollDetails.getCurrentMonth());
            ps.setDouble(13, payrollDetails.getEightyPercentSalary());
            ps.setDouble(14, payrollDetails.getHundredPercentSalary());
            ps.setInt(15, payrollDetails.getIllnessDays());
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
        PreparedStatement salaryStmt =
                conn.prepareStatement("DELETE FROM salary WHERE payroll_id = ?");
        salaryStmt.setLong(1, payrollId);
        salaryStmt.executeUpdate();

        // Удаление строк из таблицы average_salary
        PreparedStatement averageSalaryStmt =
                conn.prepareStatement("DELETE FROM average_salary WHERE payroll_id = ?");
        averageSalaryStmt.setLong(1, payrollId);
        averageSalaryStmt.executeUpdate();

        // Удаление строк из таблицы payroll_months
        PreparedStatement payrollMonthsStmt =
                conn.prepareStatement("DELETE FROM payroll_months WHERE payroll_id = ?");
        payrollMonthsStmt.setLong(1, payrollId);
        payrollMonthsStmt.executeUpdate();

        // Удаление строк из таблицы sick_month_days
        PreparedStatement sickMonthDaysStmt =
                conn.prepareStatement("DELETE FROM sick_month_days WHERE payroll_id = ?");
        sickMonthDaysStmt.setLong(1, payrollId);
        sickMonthDaysStmt.executeUpdate();

        // Удаление строк из таблицы payroll_details
        PreparedStatement payrollDetailsStmt =
                conn.prepareStatement("DELETE FROM payroll_details WHERE payroll_id = ?");
        payrollDetailsStmt.setLong(1, payrollId);
        payrollDetailsStmt.executeUpdate();
    }
}
