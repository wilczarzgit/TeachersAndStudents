package com.tomaszwilczynski.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends PhysicalEntity {
    @NotBlank(message = "Kierunek jest wymagany.")
    private String field;

    @ManyToMany(mappedBy = "students")
    private Set<Teacher> teachers;

    public Student() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
