package com.tomaszwilczynski.repository;

import com.tomaszwilczynski.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByFirstNameContainingAndSecondNameContaining(String namePart, String secondNamePart, Pageable page);
    Page<Student> findByTeachers_Id(Long id, Pageable pageable);
}
