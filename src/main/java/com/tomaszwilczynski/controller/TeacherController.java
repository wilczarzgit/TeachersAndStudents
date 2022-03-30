package com.tomaszwilczynski.controller;

import com.tomaszwilczynski.model.Teacher;
import com.tomaszwilczynski.repository.TeacherRepository;
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
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherRepository teacherRepository;
    private Logger logger = LoggerFactory.getLogger(TeacherController.class);

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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

        var sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        var pageable = PageRequest.of(pageNr - 1, 5, sort);
        var teacherPage = teacherRepository.findAll(pageable);

        model.addAttribute("teachers", teacherPage.getContent());
        model.addAttribute("currentPage", pageNr);
        model.addAttribute("totalPages", teacherPage.getTotalPages());
        model.addAttribute("totalItems", teacherPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "teachers";
    }

    @PostMapping("/search")
    public String searchTeachers(@RequestParam String firstName,
                                 @RequestParam String secondName,
                                 Model model) {
        var sort = Sort.by("secondName").ascending();
        var pageable = PageRequest.of(0, 5, sort);
        var studentPage = teacherRepository.findByFirstNameContainingAndSecondNameContaining(firstName, secondName, pageable);

        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);

        model.addAttribute("teachers", studentPage.getContent());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());

        model.addAttribute("sortField", "secondName");
        model.addAttribute("sortDir", "asc");
        model.addAttribute("reverseSortDir", "desc");
        return "teachers";
    }
}
