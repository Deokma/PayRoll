package by.popolamov.cursework.listeners;

import by.popolamov.cursework.gui.dialogs.PayrollDetailsDialog;
import by.popolamov.cursework.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Listener для формирования больничной выписки в формате Excel
 *
 * @author Denis Popolamov
 */
public class ExcelSaveListener implements ActionListener {
    private PayrollDetailsDialog dialog;
    AverageSalary averageSalary;
    PayrollDetails payrollDetails;
    PayrollMonths payrollMonths;
    Salary salary;
    SickMonthDays sickMonthDays;

    public ExcelSaveListener(PayrollDetailsDialog dialog, AverageSalary averageSalary,
                             PayrollDetails payrollDetails, PayrollMonths payrollMonths,
                             Salary salary, SickMonthDays sickMonthDays) {
        this.dialog = dialog;
        this.averageSalary = averageSalary;
        this.payrollDetails = payrollDetails;
        this.payrollMonths = payrollMonths;
        this.salary = salary;
        this.sickMonthDays = sickMonthDays;
    }

    String[] headers = {"Месяцы, взятые для\n исчисления помощи\n",
            "Количество\n календарных\n дней (часов)",
            "Командировки,\nбольничные,\nотпускные",
            "Остаток\n календарных\n дней",
            "Сумма\n фактического\n заработка\n (руб.)",
            "Средний дневной\n (почасовой)\n фактический заработок\n (руб.)"};
    List<String> data = new ArrayList<>();
    String[] totalRow;

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить в Excel");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Создаем экземпляр класса XSSFWorkbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            for (int i = 0; i < payrollMonths.getMonth().size(); i++) {
                String strMonth = payrollMonths.getMonth().get(i);
                Integer iMonthDays = sickMonthDays.getMonthDays().get(i);
                Integer iSickDays = sickMonthDays.getSickMonthDays().get(i);
                Integer iRemainingDays = sickMonthDays.getRemainingCalendarDays().get(i);
                Double dSalary = salary.getSalary().get(i);
                Double dAverageSalary = averageSalary.getAverageSalary().get(i);

                List<String> rowData = new ArrayList<>();
                rowData.add(strMonth);
                rowData.add(iMonthDays.toString());
                rowData.add(iSickDays.toString());
                rowData.add(iRemainingDays.toString());
                rowData.add(dSalary.toString());
                rowData.add(dAverageSalary.toString());

                data.add(rowData.toString());
            }

            totalRow = new String[]{"ИТОГО:", String.valueOf(payrollDetails.getTotalMonthDays()),
                    String.valueOf(payrollDetails.getTotalSickDates()), String.valueOf(payrollDetails.getTotalRemainingDays()),
                    String.valueOf(payrollDetails.getTotalSalary()), String.valueOf(payrollDetails.getTotalAverageSalary())};

            // Создаем экземпляр класса XSSFSheet
            XSSFSheet sheet = workbook.createSheet("Расчет больничного");

            // Создаем первую строку
            XSSFRow row1 = sheet.createRow(0);
            row1.setHeightInPoints(16);

            // Создаем объединенные ячейки для первой строки
            CellRangeAddress mergedRegion1 = new CellRangeAddress(0, 0, 0, 5);
            sheet.addMergedRegion(mergedRegion1);

            // Создаем ячейку для первой строки
            XSSFCell cell1 = row1.createCell(0);
            cell1.setCellValue("РАСЧЕТ БОЛЬНИЧНОГО");
            cell1.setCellStyle(getHeaderStyle(workbook));

            // Создаем вторую строку
            XSSFRow row2 = sheet.createRow(1);
            row2.setHeightInPoints(14);

            // Создаем объединенные ячейки для второй строки
            CellRangeAddress mergedRegion2 = new CellRangeAddress(1, 1, 0, 5);
            sheet.addMergedRegion(mergedRegion2);

            // Создаем ячейку для второй строки
            XSSFCell cell2 = row2.createCell(0);
            cell2.setCellValue(payrollDetails.getUserSurName() + " " + payrollDetails.getUserName() + " " + payrollDetails.getUserPatronymic());
            cell2.setCellStyle(getHeaderStyle(workbook));

            // Создаем третью строку
            XSSFRow row3 = sheet.createRow(2);
            row3.setHeightInPoints(14);

            // Создаем объединенные ячейки для третьей строки
            CellRangeAddress mergedRegion3 = new CellRangeAddress(2, 2, 0, 5);
            sheet.addMergedRegion(mergedRegion3);

            // Создаем ячейку для третьей строки
            XSSFCell cell3 = row3.createCell(0);
            cell3.setCellValue("наименование должности");
            cell3.setCellStyle(getSubHeaderStyle(workbook));

            // Создаем третью строку
            XSSFRow row4 = sheet.createRow(3);
            row4.setHeightInPoints(14);

            // Создаем объединенные ячейки для третьей строки
            CellRangeAddress mergedRegion4 = new CellRangeAddress(3, 3, 0, 5);
            sheet.addMergedRegion(mergedRegion4);

            // Создаем ячейку для третьей строки
            XSSFCell cell4 = row4.createCell(0);
            cell4.setCellValue("Период болезни с " + payrollDetails.getStartIllnessDate() + " по " + payrollDetails.getEndIllnessDate()
                    + " = " + payrollDetails.getIllnessDays() + " дней");
            cell4.setCellStyle(getSubHeaderStyle(workbook));

            // Создаем третью строку
            XSSFRow row5 = sheet.createRow(4);
            row5.setHeightInPoints(18);

            // Создаем объединенные ячейки для третьей строки
            CellRangeAddress mergedRegion5 = new CellRangeAddress(4, 4, 0, 5);
            sheet.addMergedRegion(mergedRegion5);

            // Создаем ячейку для третьей строки
            XSSFCell cell5 = row5.createCell(0);
            cell5.setCellValue("СПРАВКА О ЗАРАБОТНОЙ ПЛАТЕ");
            cell5.setCellStyle(getHeaderStyle(workbook));

            int rowNum = 5;
            Row row = sheet.createRow(rowNum);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font);

            // Заполнение таблицы заголовками столбцов
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(cellStyle);
            }
            font.setBold(false);
            for (String rowValues : data) {
                rowNum++;
                row = sheet.createRow(rowNum);
                String[] rowValuesArray = rowValues.substring(1, rowValues.length() - 1).split(", ");
                for (int i = 0; i < rowValuesArray.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(rowValuesArray[i]);
                    cell.setCellStyle(cellStyle);
                    sheet.autoSizeColumn(i);
                }
            }

            // Добавление строки ИТОГО
            rowNum++;
            row = sheet.createRow(rowNum);
            for (int i = 0; i < totalRow.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(totalRow[i]);
                cell.setCellStyle(cellStyle);
                sheet.autoSizeColumn(i);
            }
            // Создаем 15 строку
            XSSFRow row14 = sheet.createRow(14);
            row14.setHeightInPoints(14);

            // Создаем объединенные ячейки для третьей строки
            CellRangeAddress mergedRegion14 = new CellRangeAddress(14, 14, 0, 5);
            sheet.addMergedRegion(mergedRegion14);

            // Создаем ячейку для третьей строки
            XSSFCell cell14 = row14.createCell(0);
            cell14.setCellValue("ПОЛАГАЕТСЯ ПОМОЩЬ");
            cell14.setCellStyle(getHeaderStyle(workbook));

            // Создаем первую строку
            XSSFRow row15 = sheet.createRow(15);
            row15.setHeightInPoints(20);
            XSSFRow row16 = sheet.createRow(16);
            row16.setHeightInPoints(90);
            // Создаем объединенные ячейки для первой строки
            sheet.addMergedRegion(new CellRangeAddress(15, 16, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(15, 16, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(15, 16, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(15, 15, 1, 3));

            // Создание таблицы "начисления пособия"
            XSSFCell cell15 = row15.createCell(0);
            cell15.setCellValue("Месяцы, колькасць \n дзен (гадзiн) \n непрацаздольнасцi");
            XSSFCell cell16 = row15.createCell(1);
            cell16.setCellValue("СУММА НАЛIЧАНАЙ ДАПАМОГI (руб.)");
            XSSFCell cell17 = row15.createCell(4);
            cell17.setCellValue("Разлiчана\n максiмальная\n сума дапамогi\n (руб.)");
            XSSFCell cell18 = row15.createCell(5);
            cell18.setCellValue("Сума дапамогi да\n выплаты (руб.)");
            XSSFCell cell19 = row16.createCell(1);
            cell19.setCellValue("За днi (гадзiны)\n у памеры 80%\n заработку\n(календарных дней)");
            XSSFCell cell20 = row16.createCell(2);
            cell20.setCellValue("За днi (гадзiны) у\n памеры 100%\n заработку\n (календарных\n дней)");
            XSSFCell cell21 = row16.createCell(3);
            cell21.setCellValue("Усяго:");

            // Заполнение итогов таблицы "начисления пособия"
            XSSFRow row17 = sheet.createRow(17);
            String[] dates = {payrollDetails.getCurrentMonth(), String.valueOf(payrollDetails.getEightyPercentSalary()), String.valueOf(payrollDetails.getHundredPercentSalary()),
                    String.valueOf(payrollDetails.getTotalSalary()), String.valueOf(payrollDetails.getTotalSalary()), String.valueOf(payrollDetails.getTotalSalary())};
            for (int i = 0; i <= 5; i++) {
                row17.createCell(i).setCellValue(dates[i]);
            }
            // Устанавливаем жирную обводку для всей таблицы "начисления пособия"
            for (int i = 15; i <= 17; i++) {
                for (int j = 0; j <= 5; j++) {
                    XSSFCell cell = sheet.getRow(i).getCell(j);
                    if (cell == null) {
                        cell = sheet.getRow(i).createCell(j);
                    }
                    cell.setCellStyle(cellStyle);
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(fileToSave + ".xlsx");
                workbook.write(out);
                workbook.close();
                JOptionPane.showMessageDialog(null,
                        "Файл успешно сохранен в формате Excel!", "Файл сохранен",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при сохранении файла!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    // Вспомогательный метод для создания стиля шрифта для подзаголовков
    private static XSSFCellStyle getSubHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}