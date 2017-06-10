package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.WeekDay;
import com.code405.entity.model.Group;
import com.code405.entity.model.Professor;
import com.code405.entity.model.Student;
import com.code405.entity.model.Timetable;
import com.code405.service.TimetableService;
import com.code405.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by birthright on 26.04.17.
 */
@RequestMapping(Routes.TIMETABLE_URI)
@Controller
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    @GetMapping("/student")
    public String indexForStudent(Model model) {
        //лень было делать нормально
        model.addAttribute("week_type", timetableService.getWeekType());
        return "timetable/student/studentIndex";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    @GetMapping("/student/week")
    public String getForWeekForStudent(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        Group group = ((Student) user.getUser()).getGroup();
        Map<WeekDay, Map<Class, Timetable>> timetable = timetableService.findWeekTimetableForStudent(group);
        request.setAttribute("timetable", timetable);
        return "timetable/student/studentWeek";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    @GetMapping("/student/day")
    public String getForDayForStudent(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        Group group = ((Student) user.getUser()).getGroup();
        Map<WeekDay, Map<Class, Timetable>> timetable = timetableService.findDayTimetableForStudent(group);
        request.setAttribute("timetable", timetable);
        return "timetable/student/studentDay";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @GetMapping("/professor")
    public String index(Model model) {
        //лень было делать нормально
        model.addAttribute("week_type", timetableService.getWeekType());
        return "timetable/professor/professorIndex";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @GetMapping("/professor/week")
    public String getForWeekForProfessor(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        Professor professor = (Professor) user.getUser();
        Map<WeekDay, Map<Class, Timetable>> timetable = timetableService.findWeekTimetableForProfessor(professor);
        request.setAttribute("timetable", timetable);
        return "timetable/professor/professorWeek";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @GetMapping("/professor/day")
    public String getForDayForProfessor(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        Professor professor = (Professor) user.getUser();
        Map<WeekDay, Map<Class, Timetable>> timetable = timetableService.findDayTimetableForProfessor(professor);
        request.setAttribute("timetable", timetable);
        return "timetable/professor/professorDay";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/employee/week")
    public String showTimetableForEmployee() {
        return "timetable/employee/index";
    }


}
