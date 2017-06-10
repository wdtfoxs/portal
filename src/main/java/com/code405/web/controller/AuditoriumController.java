package com.code405.web.controller;

import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.WeekDay;
import com.code405.entity.model.Auditorium;
import com.code405.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/auditorium")
@Controller
public class AuditoriumController {
    private final AuditoriumService auditoriumService;

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("days", WeekDay.values());
        model.addAttribute("times", Class.values());
        return "auditoriums/index";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).PROFESSOR)")
    @GetMapping("/free")
    public String getAuditoriums(@RequestParam WeekDay day, @RequestParam Class time, Model model) {
        List<Auditorium> freeAuditoriums = auditoriumService.getFreeAuditoriums(day, time);
        model.addAttribute("auditoriums", freeAuditoriums);
        model.addAttribute("time", time.getTime());
        return "auditoriums/auditoriums";
    }

}
