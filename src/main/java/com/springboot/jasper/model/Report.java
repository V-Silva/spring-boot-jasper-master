package com.springboot.jasper.model;

import org.springframework.context.annotation.Description;

import java.io.Serializable;

@Description(value = "Report Model Definition")
public class Report implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String firstName;
    private String lastName;
    private String age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "Report{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
