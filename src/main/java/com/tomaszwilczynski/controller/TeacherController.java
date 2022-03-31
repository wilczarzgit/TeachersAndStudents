package com.tomaszwilczynski.controller;

import com.tomaszwilczynski.model.Teacher;
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
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private Logger logger = LoggerFactory.getLogger(TeacherController.class);

    public TeacherController(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String viewTeachers(Model model) {
        return getTeachers(1, "secondName", "asc", model);
    }

    @GetMapping("/new")
    public String addNewTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "editTeacher";
    }

    @PostMapping("/save")
    public String saveTeacher(@ModelAttribute("teacher") @Valid Teacher teacher,
                              BindingResult bindingResult,
                              Model model) {
        if(bindingResult.hasErrors()) {
            return "/editTeacher";
        }
        teacherRepository.save(teacher);
        viewTeachers(model);
        logger.info("Teacher with id {} was successfully saved.", teacher.getId());
        return "teachers";
    }

    @GetMapping("/delete/{teacherId}")
    public String deleteTeacher(@PathVariable long teacherId, Model model) {
        logger.info("Teacher with id {} was deleted.", teacherId);
        teacherRepository.deleteById(teacherId);
        viewTeachers(model);
        return "teachers";
    }

    @GetMapping("/edit/{teacherId}")
    public String editTeacher(@PathVariable long teacherId, Model model) {
        var teacher = teacherRepository.findById(teacherId);
        teacher.ifPresent(t -> model.addAttribute("teacher", t));
        return "editTeacher";
    }

    @GetMapping("/page/{pageNr}")
    public String getTeachers(@PathVariable Integer pageNr,
                              @RequestParam("sortField") String sortField,
                              @RequestParam("sortDir") String sortDir,
                              Model model) {
        var sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var page = teacherRepository.findAll(pageable);
        addPageToModel(model, page, pageNr, sortField, sortDir);
        return "teachers";
    }

    @PostMapping("/search")
    public String searchTeachers(@RequestParam String firstName,
                                 @RequestParam String secondName,
                                 Model model) {
        var sort = Sort.by("secondName").ascending();
        var pageable = PageRequest.of(0, 5, sort);
        var page = teacherRepository.findByFirstNameContainingAndSecondNameContaining(firstName, secondName, pageable);

        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);
        addPageToModel(model, page, 1, "secondName", "asc");
        return "teachers";
    }

    @GetMapping("/of/{studentId}")
    public String viewTeachersOfStudent(@PathVariable Long studentId, Model model) {
        return viewTeachersOfStudentPaged(studentId, 1, "secondName", "asc", model);
    }

    @GetMapping("/of/{studentId}/page/{pageNr}")
    public String viewTeachersOfStudentPaged(@PathVariable Long studentId,
                                             @PathVariable Integer pageNr,
                                             @RequestParam("sortField") String sortField,
                                             @RequestParam("sortDir") String sortDir,
                                             Model model) {
        var sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var page = teacherRepository.findByStudents_Id(studentId, pageable);
        var student = studentRepository.findById(studentId);
        student.ifPresent(s -> model.addAttribute("student", s) );
        addPageToModel(model, page, pageNr, sortField, sortDir);
        return "teachersOfStudent";
    }

    @GetMapping("/unlink")
    public String unlinkStudent(@RequestParam Long studentId,
                                @RequestParam Long teacherId,
                                Model model) {
        var teacher = teacherRepository.findById(teacherId);
        var student = studentRepository.findById(studentId);
        if(student.isPresent() && teacher.isPresent()) {
            teacher.get().getStudents().remove(student.get());
            teacherRepository.save(teacher.get());
        }
        return viewTeachersOfStudent(studentId, model);
    }

    @GetMapping("/link")
    public String prepareToLinkStudentAndTeacher(@RequestParam Long studentId, Model model) {
        return prepareToLinkStudentAndTeacherPaged(studentId, 1, "secondName", "asc", model);
    }

    @GetMapping("/link/page/{pageNr}")
    public String prepareToLinkStudentAndTeacherPaged(@RequestParam Long studentId,
                                                      @PathVariable Integer pageNr,
                                                      @RequestParam("sortField") String sortField,
                                                      @RequestParam("sortDir") String sortDir,
                                                      Model model) {
        var sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var page = teacherRepository.findAll(pageable);
        var student = studentRepository.findById(studentId);
        student.ifPresent(s -> model.addAttribute("student", s) );
        addPageToModel(model, page, pageNr, sortField, sortDir);
        return "teachersToLink";
    }

    @GetMapping("/finalizeLink")
    public String linkStudentAndTeacher(@RequestParam Long teacherId,
                                        @RequestParam Long studentId,
                                        Model model) {
        var teacher = teacherRepository.findById(teacherId);
        var student = studentRepository.findById(studentId);
        if(student.isPresent() && teacher.isPresent()) {
            teacher.get().getStudents().add(student.get());
            teacherRepository.save(teacher.get());
            logger.info("Utworzono połączenie pomiędzy nauczycielem {} a studentem {}", teacherId, studentId);
        }
        return viewTeachersOfStudent(teacherId, model);
    }

    private void addPageToModel(Model model, Page<Teacher> page, Integer pageNr, String sortField, String sortDir) {
        model.addAttribute("teachers", page.getContent());
        model.addAttribute("currentPage", pageNr);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }
}
