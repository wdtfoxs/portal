package com.code405.web.controller;

import com.code405.entity.embeddable.Address;
import com.code405.entity.enumeration.Semester;
import com.code405.entity.model.Auditorium;
import com.code405.entity.model.Building;
import com.code405.entity.model.Discipline;
import com.code405.entity.model.Position;
import com.code405.repository.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by Suren on 01.06.2017.
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
public class AdminController {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private AuditoriumRepository auditoriumRepository;
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping
    public String adminView() {
        return "admin/index";
    }

    @GetMapping("/users")
    public String users() {
        return "admin/users";
    }

    @GetMapping("/users/create_user")
    public String createUsers() {
        return "admin/create_user";
    }

    @GetMapping("users/all_users")
    public String allUsers() {
        return "admin/all_users";
    }

    @GetMapping("/users/create_professor")
    public String createProfessor(Model model) {
        model.addAttribute("positions", positionRepository.findAll());
        model.addAttribute("disciplines", disciplineRepository.findAll());
        return "admin/professor_create";
    }

    @GetMapping("/users/create_student")
    public String createStudent(Model model) {
        model.addAttribute("groups", groupRepository.findAll(new Sort(Sort.Direction.ASC, "groupCode")));
        return "admin/create_student";
    }

    @GetMapping("/users/create_employee")
    public String createEmployee() {
        return "admin/create_employee";
    }

    @GetMapping("/discipline")
    public String discipline() {
        return "admin/discipline";
    }

    @GetMapping("/create_discipline")
    public String createDiscipline() {
        return "admin/create_discipline";
    }

    @PostMapping("/create_discipline")
    public RedirectView saveDiscipline(@RequestParam String name,
                                       @RequestParam Integer semester,
                                       @RequestParam(required = false) String physical) {
        try {
            disciplineRepository.save(Discipline.builder().discipline(name).physicalCulture(physical != null).semester(Semester.valueOf(semester)).build());
            return new RedirectView("/admin/create_discipline?success");
        } catch (Exception e) {
            return new RedirectView("/admin/create_discipline?error");
        }
    }

    @GetMapping("/all_disciplines")
    public String allDisciplines() {
        return "admin/all_disciplines";
    }

    @GetMapping("/createBuilding")
    public String createBuilding() {
        return "admin/create_building";
    }

    @PostMapping("/createBuilding")
    public RedirectView saveBuilding(@RequestParam String name) {
        try {
            buildingRepository.save(Building.builder().name(name).address(new Address(22.33, 224.55)).build());
            return new RedirectView("/admin/createBuilding?success");
        } catch (Exception e) {
            return new RedirectView("/admin/createBuilding?error");
        }
    }

    @GetMapping("/createAuditorium")
    public String createAuditorium(Model model) {
        model.addAttribute("buildings", buildingRepository.findAll());
        return "admin/create_auditorium";
    }

    @PostMapping("/createAuditorium")
    public RedirectView saveAuditorium(@RequestParam Long building,
                                       @RequestParam Integer number) {
        try {
            auditoriumRepository.save(Auditorium.builder().number(number).building(buildingRepository.findOne(building)).capacity(30).build());
            return new RedirectView("/admin/createAuditorium?success");
        } catch (Exception e) {
            return new RedirectView("/admin/createAuditorium?error");
        }
    }


    @GetMapping("/createPosition")
    public String createPosition() {
        return "admin/create_position";
    }

    @PostMapping("/createPosition")
    public RedirectView savePosition(@RequestParam String position) {
        try {
            positionRepository.save(Position.builder().position(position).build());
            return new RedirectView("/admin/createPosition?success");
        } catch (Exception e) {
            return new RedirectView("/admin/createPosition?error");
        }
    }
}
