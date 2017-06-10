package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.entity.model.*;
import com.code405.service.DayBookService;
import com.code405.service.DisciplineService;
import com.code405.service.TimetableService;
import com.code405.util.UserDetailsImpl;
import com.code405.web.dto.JournalDayBookDTO;
import com.code405.web.dto.JournalDisciplineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by wdtfoxs on 25.05.2017.
 */
@Controller
@RequestMapping(Routes.JOURNAL_URI)
@PreAuthorize("isAuthenticated()")
public class JournalController {

    @Autowired
    private DisciplineService discipline;
    @Autowired
    private DayBookService dayBookService;
    @Autowired
    private TimetableService timetableService;

    @GetMapping("/student")
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    public String indexStudent(@AuthenticationPrincipal UserDetailsImpl user,
                               Model model, @RequestParam(value = "discipline", required = false) Long discipline_id) {
        Student student = (Student) user.getUser();
        model.addAttribute("disciplines", discipline.disciplinesForJournalOfStudents(student.getAdmissionDate(), student.getGroup().getGroupCode()));
        if (discipline_id != null) {
            model.addAttribute("journal", dayBookService.getJournal(((Student) user.getUser()).getGroup(), discipline_id));
            model.addAttribute("discipline", discipline_id);
        } else
            model.addAttribute("discipline", -1);
        return Routes.JOURNAL_STUDENT_VIEW;
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    public String indexStudent(Model model, @RequestParam(value = "discipline", required = false) Long discipline_id,
                               @RequestParam(value = "group", required = false) String codeGroup,
                               @RequestParam(value = "course", required = false) Integer course) {
        if (discipline_id != null) {
            model.addAttribute("journal", dayBookService.getJournal(Group.builder().groupCode(codeGroup).build(), discipline_id));
            model.addAttribute("discipline", discipline_id);
            model.addAttribute("course", course);
            model.addAttribute("group", codeGroup);
        }
        return Routes.JOURNAL_EMPLOYEE_VIEW;
    }

    @GetMapping(value = "/employee", params = "disc")
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @ResponseBody
    public List<JournalDisciplineDTO> getJournalDiscipline(@RequestParam int course,
                                                           @RequestParam String group) {
        return discipline.disciplinesForJournalEmployeeOfStudents(course, group);
    }

    @GetMapping("/professor")
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    public String indexProfessor(@AuthenticationPrincipal UserDetailsImpl user, Model model,
                                 @RequestParam(value = "group_discipline", required = false) String group_disc) {
        model.addAttribute("disciplines", timetableService.getGroupsAndDisciplinesForProfessor((Professor) user.getUser()));
        if (group_disc != null) {
            String[] params = group_disc.split("_");
            model.addAttribute("journal", dayBookService.getJournal(Group.builder().groupCode(params[0]).build(), Long.parseLong(params[1])));
            model.addAttribute("current_disc", params[1]);
            model.addAttribute("current_group", params[0]);
        }
        return Routes.JOURNAL_PROFESSOR_VIEW;
    }

    @PostMapping(value = "/professor", params = {"lesson"})
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @ResponseBody
    public String addLesson(@RequestBody List<JournalDayBookDTO> list) throws ParseException {
        dayBookService.saveLessonsResult(list);
        return "200";
    }
}
