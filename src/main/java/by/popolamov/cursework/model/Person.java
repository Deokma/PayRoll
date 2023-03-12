package by.popolamov.cursework.model;

import java.util.Date;

/**
 * @author Denis Popolamov
 */
public class Person {
    private Long userId;
    private String surname; // Фамилия
    private String name; // Имя
    private String patronymic; // Отчество

    private Date firtDateOfIllnes;
    private Date secondDateOfIllness;

    public Person(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
