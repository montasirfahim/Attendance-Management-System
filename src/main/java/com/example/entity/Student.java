package com.example.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String roll;
    private String department;
    private String session;

    public Student(Integer id) {
        this.id = id;
    }
    public Student(){}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public String getDepartment() {
        return department;
    }

    public String getSession() {
        return session;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
