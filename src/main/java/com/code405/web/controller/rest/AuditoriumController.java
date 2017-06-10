package com.code405.web.controller.rest;

import com.code405.entity.util.Select;
import com.code405.service.AuditoriumService;
import com.code405.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Peter Kozlovsky on 28.05.17.
 */
@RestController("auditoriumRestController")
public class AuditoriumController {
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private TimetableService timetableService;


    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/auditorium", params = "building")
    public List<Select> getAuditoriumsByBuilding(@RequestParam("building") Long building) {
        return auditoriumService.getAuditoriumsByBuilding(building);
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/auditorium", params = {"classQ", "week", "groupCode", "auditorium"})
    public boolean findAuditorium(@RequestParam("classQ") String classQ,
                                  @RequestParam("week") String week,
                                  @RequestParam("groupCode") String groupCode,
                                  @RequestParam("auditorium") Long auditorium) {
        return timetableService.findTimetableBy(classQ, week, groupCode, auditorium);
    }
}
