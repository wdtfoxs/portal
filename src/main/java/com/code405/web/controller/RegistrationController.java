package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.entity.model.User;
import com.code405.service.interfaces.IUserService;
import com.code405.web.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


/**
 * Created by Birthright on 30.04.2016.
 */
@Controller
@Log4j2
public class RegistrationController {
    private static final String modelName = "user";
    @Autowired
    private IUserService userService;

    @GetMapping(Routes.REGISTRATION_URI)
    public String index(ModelMap modelMap) {
        if (!modelMap.containsAttribute(modelName)) {
            modelMap.addAttribute(modelName, new UserDto());
        }
        return Routes.REGISTRATION_VIEW;
    }

    /**
     * @param userDto - User Data Transfer Object, need to validate our registration form
     */
    @PostMapping(Routes.REGISTRATION_URI)
    public RedirectView registerUser(@Valid @ModelAttribute(modelName) UserDto userDto,
                                     BindingResult result, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(modelName, userDto);
        if (result.hasErrors()) {
            return new RedirectView(Routes.REGISTRATION_URI);
        }
        User registered = userService.createUserAccount(userDto);
        if (registered == null) {
            result.rejectValue("email", "register.message.email_error");
            result.rejectValue("username", "register.message.username_error");
            return new RedirectView(Routes.REGISTRATION_URI);
        }
        return new RedirectView(Routes.REGISTRATION_SUCCESS_URI);
    }


    /**
     * Show success page after POST registration.
     *
     * @return If some wise guy will go here after not registering - redirect to the root, else show page.
     */
    @GetMapping(value = Routes.REGISTRATION_URI, params = "success")
    public String successRegistration(ModelMap modelMap) {
        if (modelMap.containsAttribute(modelName)) {
            return Routes.REGISTRATION_SUCCESS_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

}
