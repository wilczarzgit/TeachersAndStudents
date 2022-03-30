package com.tomaszwilczynski.repository;

import com.tomaszwilczynski.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Page<Teacher> findByFirstNameContainingAndSecondNameContaining(String namePart, String secondNamePart, Pageable page);
}
