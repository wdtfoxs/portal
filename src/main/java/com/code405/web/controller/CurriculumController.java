package com.code405.web.controller;

import com.code405.entity.model.Group;
import com.code405.entity.model.Student;
import com.code405.repository.models.GroupRepository;
import com.code405.service.CurriculumService;
import com.code405.service.GroupService;
import com.code405.util.UserDetailsImpl;
import com.code405.web.dto.CurriculumView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/curriculum")
@Controller
public class CurriculumController {
    private final CurriculumService curriculumService;
    private final GroupRepository groupRepository;
    private final GroupService groupService;

    @Autowired
    public CurriculumController(CurriculumService curriculumService, GroupRepository groupRepository, GroupService groupService) {
        this.curriculumService = curriculumService;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }


    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    @GetMapping("/student")
    public String indexStudent(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        Student student = (Student) user.getUser();
        List<CurriculumView> curriculumViews = curriculumService.getCurriculum(student.getGroup());
        request.setAttribute("curriculumView", curriculumViews);
        return "curriculum/student/index";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/employee")
    public String indexEmployee() {
        return "curriculum/employee/index";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/employee/groups")
    public String employeeGroups(@RequestParam Integer course, Model model) {
        List<Group> groupsByCourse = groupService.getGroupsByCourse(course);
        model.addAttribute("groups", groupsByCourse);
        return "curriculum/employee/groups";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @GetMapping("/employee/curriculum")
    public String employeeGet(@RequestParam String group, Model model) {
        Group search = groupRepository.findOne(group);
        List<CurriculumView> curriculumViews = curriculumService.getCurriculum(search);
        model.addAttribute("curriculumView", curriculumViews);
        return "curriculum/employee/curriculum";
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE)")
    @PostMapping("/employee/save")
    public RedirectView saveCurriculum(@RequestParam("course") Integer course,
                                       @RequestParam("groupCode") String groupCode,
                                       @RequestParam("file") MultipartFile file,
                                       RedirectAttributes attributes) {
        String message;
        try {
            curriculumService.saveOrUpdate(course, groupCode, file);
            message = "Учебный план у " + groupCode + " успешно обновлен";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Невалидный файл. Поправьте его и попробуйте еще раз";
        }
        attributes.addFlashAttribute("message", message);
        return new RedirectView("/curriculum/employee");
    }
}
