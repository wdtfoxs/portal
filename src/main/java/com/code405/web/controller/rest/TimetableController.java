package com.code405.web.controller.rest;

import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.Semester;
import com.code405.entity.enumeration.WeekDay;
import com.code405.entity.model.Building;
import com.code405.entity.model.Group;
import com.code405.entity.util.Select;
import com.code405.repository.models.DisciplineRepository;
import com.code405.service.GroupService;
import com.code405.service.ProfessorService;
import com.code405.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Peter Kozlovsky on 27.05.17.
 */
@RestController("timetableRestController")
public class TimetableController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private DisciplineRepository disciplineRepository;


    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/timetable/groups")
    public List<Group> getGroupsByCourse(@RequestParam Integer course) {
        return groupService.getGroupsByCourse(course);
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/timetable/forGroup")
    public Map<WeekDay, Map<Class, Map<String, List<Select>>>> getTimetableForGroup(@RequestParam String group) {
        return timetableService.getTimetableForEmployeeByGroup(group);
    }
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/timetable/getProfessors", params = "id")
    public List<Select> getProfessorsByDiscipline(@RequestParam Long id) {
        return professorService.getProfessorsByDisciplineId(id);
    }
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/timetable/check", params = {"week", "day", "groupCode", "professor", "classQ"})
    public boolean checkTimetable(@RequestParam String week,
                                  @RequestParam String day,
                                  @RequestParam String groupCode,
                                  @RequestParam Long professor,
                                  @RequestParam String classQ){
       return timetableService.checkTimetable(week, day, groupCode, professor, classQ);

    }
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @PostMapping(value = "/timetable/save", params = {"timetables", "groupCode"})
    public boolean saveTimetable(@RequestParam String timetables,
                                 @RequestParam String groupCode){
        try {
            return timetableService.saveTimetable(timetables, groupCode);
        } catch (Exception e) {
           return false;
        }
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/timetable/buildings")
    public List<Building> getBuildings(){
        return timetableService.getBuildings();
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping(value = "/timetable/disciplines")
    public List<Object> getDisciplines(@RequestParam String groupCode){
        return disciplineRepository.getDisciplinesForExam(groupCode,Semester.getCurrentSemester().toString());
    }


}
