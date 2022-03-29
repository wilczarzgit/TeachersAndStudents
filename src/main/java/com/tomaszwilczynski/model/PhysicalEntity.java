package com.tomaszwilczynski.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public class PhysicalEntity extends BaseEntity {
    @NotBlank(message = "Imię jest wymagane.")
    @Size(min = 3, message = "Imię musi mieć przynajmniej trzy znaki.")
    private String firstName;

    @NotBlank(message = "Nazwisko jest wymagane.")
    @Size(min = 3, message = "Nazwisko musi mieć przynajmniej trzy znaki.")
    private String secondName;

    @Min(value = 18, message = "Student musi mieć przynajmniej 18 lat.")
    private int age;

    @NotBlank(message = "E-mail jest wymagany.")
    @Email(message = "Niepoprawny format e-mail.")
    private String email;

    public PhysicalEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
