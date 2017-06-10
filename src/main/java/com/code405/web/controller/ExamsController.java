package com.code405.web.controller;

import com.code405.entity.model.Professor;
import com.code405.entity.model.Student;
import com.code405.service.ExamsService;
import com.code405.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/exams")
@Controller
public class ExamsController {
    private final ExamsService examsService;

    @Autowired
    public ExamsController(ExamsService examsService) {
        this.examsService = examsService;
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    @GetMapping("/student")
    public String indexForStudent(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
        Student student = (Student) user.getUser();
        model.addAttribute("exams", examsService.getExams(student));
        return "exams/student/index";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @GetMapping("/professor")
    public String indexForProfessor(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
        Professor professor = (Professor) user.getUser();
        model.addAttribute("exams", examsService.getExams(professor));
        return "exams/professor/index";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/employee")
    public String indexForEmployee() {
        return "exams/employee/index";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/employee/show", params = "group")
    public String showForEmployee(@RequestParam String group, Model model) {
        model.addAttribute("exams", examsService.getExams(group));
        return "exams/employee/show";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @PostMapping(value = "/employee/add")
    @ResponseBody
    public ResponseEntity<String> addExam(@RequestParam String createDate,
                                          @RequestParam String beginningTime,
                                          @RequestParam String endTime,
                                          @RequestParam String type,
                                          @RequestParam Long auditorium,
                                          @RequestParam Long discipline,
                                          @RequestParam Long professor,
                                          @RequestParam String groupCode) {

        try {
            return new ResponseEntity<>(examsService.saveExam(createDate, beginningTime, endTime, type, auditorium, discipline, professor, groupCode), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Невалидные данные", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @PostMapping(value = "/employee/update")
    @ResponseBody
    public ResponseEntity<String> updateExam(@RequestParam String createDate,
                                          @RequestParam String beginningTime,
                                          @RequestParam String endTime,
                                          @RequestParam Long id) {

        try {
            return new ResponseEntity<>(examsService.updateExam(createDate, beginningTime, endTime, id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Невалидные данные", HttpStatus.BAD_REQUEST);
        }
    }

}
