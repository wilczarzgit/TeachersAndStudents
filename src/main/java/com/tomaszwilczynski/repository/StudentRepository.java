package com.tomaszwilczynski.repository;

import com.tomaszwilczynski.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
