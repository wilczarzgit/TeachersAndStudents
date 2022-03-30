package com.tomaszwilczynski.controller;

import com.tomaszwilczynski.model.Student;
import com.tomaszwilczynski.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentRepository studentRepository) {
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

        var sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var studentPage = studentRepository.findAll(pageable);

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", pageNr);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

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

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());

        model.addAttribute("sortField", "secondName");
        model.addAttribute("sortDir", "asc");
        model.addAttribute("reverseSortDir", "desc");
        return "students";
    }
}
