package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.entity.model.Reference;
import com.code405.entity.model.Role;
import com.code405.entity.model.Student;
import com.code405.service.ReferenceService;
import com.code405.service.StudentService;
import com.code405.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wdtfoxs on 16.05.2017.
 */
@Controller
@RequestMapping(Routes.CABINET_URI)
@PreAuthorize("isAuthenticated()" +
        " and !hasRole(T(com.code405.entity.enumeration.RoleEnumeration).TEMPORARY_ACCESS)")
public class CabinetController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ReferenceService referenceService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
        for (Role r : user.getUser().getRoles()) {
            switch (r.getName()){
                case STUDENT: {
                    model.addAttribute("student", user.getUser());
                    model.addAttribute("documents", referenceService.getListByStudent((Student) user.getUser()));
                    break;
                }
                case PROFESSOR: {
                    model.addAttribute("professor", user.getUser());
                    break;
                }
                case EMPLOYEE: {
                    model.addAttribute("employee", user.getUser());
                    model.addAttribute("documents", referenceService.getListForCollaborator());
                    break;
                }
                case ADMIN:
                    return "redirect:/admin";
            }
        }
        return Routes.CABINET_VIEW;
    }

    @PostMapping(params = {"order", "count", "type"})
    @ResponseBody
    public List<Reference> orderDocument(@RequestParam Integer count,
                             @RequestParam Integer type,
                             @AuthenticationPrincipal UserDetailsImpl user){

        return referenceService.orderDocument(count, type, (Student) user.getUser());
    }

    @PostMapping(params = {"ready", "id", "status"})
    @ResponseBody
    public String readyDocument(@RequestParam Long id,
                                @RequestParam Integer status){
        referenceService.updateStatus(id, status);
        return "200";
    }
}
