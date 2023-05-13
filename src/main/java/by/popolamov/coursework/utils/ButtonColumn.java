package by.popolamov.coursework.utils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс представляет собой реализацию столбца таблицы, который содержит кнопки.
 * Он используется для добавления кнопок в столбец таблицы и
 * выполнения заданные действий при нажатии на эти кнопки.
 *
 * @author Denis Popolamov
 */
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JTable tbl; // Таблица, к которой привязан столбец
    private Action action; // Действие, которое выполняется при нажатии на кнопку
    private JButton btnRender; // Кнопка для отображения ячейки
    private JButton btnEdit; // Кнопка для редактирования ячейки
    private Object editorValue; // Значение ячейки, которое редактируется

    ImageIcon buttonIcon =
            //new ImageIcon("src/main/resources/images/details-icon.png"); // Получение изображения для кнопки
            new ImageIcon(ClassLoader.getSystemResource("resources/images/details-icon.png"));
    // Получение изображения для кнопки
    Image image =
            buttonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    // Масштабирование изображения
    ImageIcon scaledButtonIcon = new ImageIcon(image); // Создание нового ImageIcon с измененным размером


    // Конструктор класса
    public ButtonColumn(JTable tbl, Action action, int column) {
        this.tbl = tbl;
        this.action = action;

        btnRender = new JButton(); // Создание кнопки для отображения ячейки
        btnEdit = new JButton(); // Создание кнопки для редактирования ячейки
        btnEdit.setFocusPainted(false); // Отключение подсветки кнопки при фокусировке
        btnEdit.addActionListener(this); // Добавление слушателя на нажатие кнопки

        TableColumnModel columnModel = tbl.getColumnModel(); // Получение модели столбцов таблицы
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
    public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // Определение цвета кнопки в зависимости от того, выбрана ли ячейка и имеет ли фокус
        if (hasFocus) {
            btnRender.setForeground(tbl.getForeground());
            btnRender.setBackground(UIManager.getColor(new Color(208, 29, 29)));
        } else if (isSelected) {
            btnRender.setForeground(tbl.getSelectionForeground());
            btnRender.setBackground(tbl.getSelectionBackground());
        } else {
            btnRender.setForeground(tbl.getForeground());
            btnRender.setBackground(UIManager.getColor(new Color(255, 0, 0)));
        }

        btnRender.setBackground(new Color(238, 238, 238)); // Установка цвета фона кнопки
        btnRender.setIcon(scaledButtonIcon);
        return btnRender;
    }

    /* Метод, возвращающий компонент, который будет использоваться для редактирования ячейки
       Сохраняет значение ячейки в editorValue и возвращает кнопку для редактирования ячейки*/
    @Override
    public Component getTableCellEditorComponent(JTable tbl, Object value,
                                                 boolean isSelected, int row, int column) {
        editorValue = value;
        btnEdit.setIcon(scaledButtonIcon);
        return btnEdit;
    }

    /* Обработчик события для нажатия на кнопку
     Определяет строку, в которой находится кнопка, и передает данные этой строки в action*/
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = tbl.convertRowIndexToModel(tbl.getEditingRow());
        Object rowData[] = new Object[tbl.getColumnCount()];
        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = tbl.getModel().getValueAt(row, i);
        }

        action.actionPerformed(new ActionEvent(rowData, ActionEvent.ACTION_PERFORMED, ""));
    }
}