package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.entity.model.Event;
import com.code405.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * Created by Peter Kozlovsky on 16.05.17.
 */
@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping(Routes.EVENT_URI)
    public String index(ModelMap modelMap) {
        modelMap.put("events",
                eventService.findNewsByPage(1, 5));
        return Routes.EVENT_VIEW;
    }

    @GetMapping(Routes.EVENT_MORE_URI)
    @ResponseBody
    public List<Event> loadMoreNews(@RequestParam("size") int size,
                                    @RequestParam("page") int page) {
        return eventService.findNewsByPage(page, size);
    }

    @GetMapping(Routes.EVENT_SHOW_URI)
    public String showNews(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.put("event", eventService.findOne(id));
        return Routes.EVENT_SHOW_VIEW;
    }

    @PostMapping(Routes.EVENT_NEW_URI)
    @PreAuthorize("hasAnyRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE,T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    public RedirectView addEvent(@RequestParam("file") MultipartFile file,
                                 @RequestParam("text") String text,
                                 @RequestParam("title") String title,
                                 @RequestParam("from") String from,
                                 @RequestParam("to") String to,
                                 @RequestParam("date") String date) {
        try {
            Event event = eventService.createEvent(file, text, title, from, to, date);
            String path = "";
            if (event != null) {
                path = "/" + event.getId();
            }
            return new RedirectView(Routes.EVENT_URI + path);
        } catch (Exception e) {
            return new RedirectView(Routes.EVENT_URI);
        }
    }

    @PostMapping(Routes.EVENT_UPDATE_URI)
    @PreAuthorize("hasAnyRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE,T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    public RedirectView updateEvent(@RequestParam(value = "file", required = false) MultipartFile file,
                                    @RequestParam("text") String text,
                                    @RequestParam("title") String title,
                                    @RequestParam("from") String from,
                                    @RequestParam("to") String to,
                                    @RequestParam("date") String date,
                                    @PathVariable("id") Long id) {
        Event event;
        try {
            event = eventService.updateEvent(id, file, text, title, from, to, date);
        } catch (Exception e) {
            return new RedirectView(Routes.EVENT_URI);
        }
        return new RedirectView(Routes.EVENT_URI + "/" + event.getId());
    }

    @PostMapping(Routes.EVENT_DELETE_URI)
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    @ResponseBody
    public String deleteEvent(@PathVariable("id") Long id) {
        eventService.delete(id);
        return "OK";
    }

    @GetMapping(value = Routes.EVENT_URI, params = {"id"})
    @PreAuthorize("hasAnyRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE,T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    @ResponseBody
    public Event getEvent(@RequestParam Long id) {
        return eventService.findOne(id);
    }
}
