// Пакет, содержащий утилитарные классы для приложения
package by.popolamov.cursework.utils;

// Импорт необходимых библиотек

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Класс ButtonColumn расширяет класс AbstractCellEditor и реализует интерфейсы TableCellRenderer, TableCellEditor, ActionListener
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JTable table; // Таблица, к которой привязан столбец
    private Action action; // Действие, которое выполняется при нажатии на кнопку
    private JButton renderButton; // Кнопка для отображения ячейки
    private JButton editButton; // Кнопка для редактирования ячейки
    private Object editorValue; // Значение ячейки, которое редактируется

    ImageIcon buttonIcon = new ImageIcon("src/main/resources/images/details-icon.png"); // Получение изображения для кнопки
    Image image = buttonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Масштабирование изображения
    ImageIcon scaledButtonIcon = new ImageIcon(image); // Создание нового ImageIcon с измененным размером


    // Конструктор класса
    public ButtonColumn(JTable table, Action action, int column) {
        this.table = table;
        this.action = action;

        renderButton = new JButton(); // Создание кнопки для отображения ячейки
        editButton = new JButton(); // Создание кнопки для редактирования ячейки
        editButton.setFocusPainted(false); // Отключение подсветки кнопки при фокусировке
        editButton.addActionListener(this); // Добавление слушателя на нажатие кнопки

        TableColumnModel columnModel = table.getColumnModel(); // Получение модели столбцов таблицы
        columnModel.getColumn(column).setCellRenderer(this); // Установка рендерера для столбца
        columnModel.getColumn(column).setCellEditor(this); // Установка редактора для столбца
    }

    // Метод получения значения редактируемой ячейки
    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    // Метод отображения ячейки таблицы
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Определение цвета кнопки в зависимости от того, выбрана ли ячейка и имеет ли фокус
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor(new Color(208, 29, 29)));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor(new Color(255, 0, 0)));
        }

        renderButton.setBackground(new Color(238, 238, 238)); // Установка цвета фона кнопки
        renderButton.setIcon(scaledButtonIcon);
        return renderButton;
    }

    // Метод, возвращающий компонент, который будет использоваться для редактирования ячейки
// Сохраняет значение ячейки в editorValue и возвращает кнопку для редактирования ячейки
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editorValue = value;
        //editButton.setText("");
        editButton.setIcon(scaledButtonIcon);
        return editButton;
    }

    // Обработчик события для нажатия на кнопку
// Определяет строку, в которой находится кнопка, и передает данные этой строки в action
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        fireEditingStopped();
        Object rowData[] = new Object[table.getColumnCount()];
        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = table.getModel().getValueAt(row, i);
        }

        action.actionPerformed(new ActionEvent(rowData, ActionEvent.ACTION_PERFORMED, ""));
    }
}