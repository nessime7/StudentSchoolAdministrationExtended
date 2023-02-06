package com.student.model;

import java.util.UUID;

public class CreateStudentRequest {
    private String name;
    private String surname;
    private String email;
    private int age;
    private UUID classId;

    public CreateStudentRequest() {
    }

    public CreateStudentRequest(String name, String surname, String email, int age, UUID classId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UUID getClassId() {
        return classId;
    }
}
