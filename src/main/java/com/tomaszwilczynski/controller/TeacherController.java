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
        return getteachers(1, "secondName", "asc", model);
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
    public String getteachers(@PathVariable Integer pageNr,
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
}
