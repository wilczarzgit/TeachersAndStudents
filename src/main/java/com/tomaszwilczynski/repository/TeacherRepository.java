package com.tomaszwilczynski.repository;

import com.tomaszwilczynski.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Page<Teacher> findByFirstNameContainingAndSecondNameContaining(String namePart, String secondNamePart, Pageable page);
    Page<Teacher> findByStudents_Id(Long id, Pageable pageable);
}
