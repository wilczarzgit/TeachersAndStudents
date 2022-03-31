package com.tomaszwilczynski.controller;

import com.tomaszwilczynski.model.Student;
import com.tomaszwilczynski.repository.StudentRepository;
import com.tomaszwilczynski.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String viewStudents(Model model) {
        return getStudents(1, "secondName", "asc", model);
    }

    @GetMapping("/new")
    public String addNewStudent(Model model) {
        model.addAttribute("student", new Student());
        return "editStudent";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") @Valid Student student,
                              BindingResult bindingResult,
                              Model model) {
        if(bindingResult.hasErrors()) {
            return "/editStudent";
        }
        studentRepository.save(student);
        viewStudents(model);
        logger.info("Student with id {} was successfully saved.", student.getId());
        return "students";
    }

    @GetMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable long studentId, Model model) {
        logger.info("Student with id {} was deleted.", studentId);
        studentRepository.deleteById(studentId);
        viewStudents(model);
        return "students";
    }

    @GetMapping("/edit/{studentId}")
    public String editStudent(@PathVariable long studentId, Model model) {
        var student = studentRepository.findById(studentId);
        student.ifPresent(s -> model.addAttribute("student", s));
        return "editStudent";
    }

    @GetMapping(value ="/page/{pageNr}")
    public String getStudents(@PathVariable Integer pageNr,
                              @RequestParam("sortField") String sortField,
                              @RequestParam("sortDir") String sortDir,
                              Model model) {
        var sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var studentPage = studentRepository.findAll(pageable);
        addPageToModel(model, studentPage, pageNr, sortField, sortDir);
        return "students";
    }

    @PostMapping("/search")
    public String searchStudents(@RequestParam String firstName,
                                 @RequestParam String secondName,
                                 Model model) {
        var sort = Sort.by("secondName").ascending();
        var pageable = PageRequest.of(0, 5, sort);
        var studentPage = studentRepository.findByFirstNameContainingAndSecondNameContaining(firstName, secondName, pageable);

        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);
        addPageToModel(model, studentPage, 1, "secondName", "desc");
        return "students";
    }

    @GetMapping("/of/{teacherId}")
    public String viewStudentsOfTeacher(@PathVariable Long teacherId, Model model) {
        return viewStudentsOfTeacherPaged(teacherId, 1, "secondName", "asc", model);
    }

    @GetMapping("/of/{teacherId}/page/{pageNr}")
    public String viewStudentsOfTeacherPaged(@PathVariable Long teacherId,
                                             @PathVariable Integer pageNr,
                                             @RequestParam("sortField") String sortField,
                                             @RequestParam("sortDir") String sortDir,
                                             Model model) {
        var sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var page = studentRepository.findByTeachers_Id(teacherId, pageable);
        var teacher = teacherRepository.findById(teacherId);
        teacher.ifPresent(t -> model.addAttribute("teacher", t) );
        addPageToModel(model, page, pageNr, sortField, sortDir);
        return "studentsOfTeacher";
    }

    @GetMapping("/unlink")
    public String unlinkTeacher(@RequestParam Long studentId,
                                @RequestParam Long teacherId,
                                Model model) {
        var teacher = teacherRepository.findById(teacherId);
        var student = studentRepository.findById(studentId);
        if(student.isPresent() && teacher.isPresent()) {
            teacher.get().getStudents().remove(student.get());
            teacherRepository.save(teacher.get());
            logger.info("Usunięto połączenie pomiędzy nauczycielem {} a studentem {}", teacherId, studentId);
        }
        return viewStudentsOfTeacher(teacherId, model);
    }

    @GetMapping("/link")
    public String prepareToLinkStudentAndTeacher(@RequestParam Long teacherId, Model model) {
        return prepareToLinkStudentAndTeacherPaged(teacherId, 1, "secondName", "asc", model);
    }

    @GetMapping("/link/page/{pageNr}")
    public String prepareToLinkStudentAndTeacherPaged(@RequestParam Long teacherId,
                                                      @PathVariable Integer pageNr,
                                                      @RequestParam("sortField") String sortField,
                                                      @RequestParam("sortDir") String sortDir,
                                                      Model model) {
        var sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var page = studentRepository.findAll(pageable);
        var teacher = teacherRepository.findById(teacherId);
        teacher.ifPresent(t -> model.addAttribute("teacher", t) );
        addPageToModel(model, page, pageNr, sortField, sortDir);
        return "studentsToLink";
    }

    @GetMapping("/finalizeLink")
    public String linkStudentAndTeacher(@RequestParam Long teacherId,
                                        @RequestParam Long studentId,
                                        Model model) {
        var student = studentRepository.findById(studentId);
        var teacher = teacherRepository.findById(teacherId);
        if(student.isPresent() && teacher.isPresent()) {
            teacher.get().getStudents().add(student.get());
            teacherRepository.save(teacher.get());
            logger.info("Utworzono połączenie pomiędzy nauczycielem {} a studentem {} ", teacherId, studentId);
        }
        return viewStudentsOfTeacher(teacherId, model);
    }

    private void addPageToModel(Model model, Page<Student> page, Integer pageNr, String sortField, String sortDir) {
        model.addAttribute("students", page.getContent());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNr);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }
}
