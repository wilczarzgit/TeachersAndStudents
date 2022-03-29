package com.tomaszwilczynski.repository;

import com.tomaszwilczynski.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
