package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.service.EventService;
import com.code405.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by birthright on 26.04.17.
 */
@Controller
public class RootController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private EventService eventService;

    @GetMapping(Routes.ROOT_URI)
    public String index(Model model, HttpSession session) {
        model.addAttribute("news", newsService.findLast3News());
        model.addAttribute("events", eventService.findLast3Event());
        if(session.getAttribute("start")==null){
            session.setAttribute("start", new Object());
            model.addAttribute("start", new Object());
        }
        return Routes.ROOT_VIEW;
    }
}
