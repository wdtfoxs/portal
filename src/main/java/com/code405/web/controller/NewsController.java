package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.entity.model.News;
import com.code405.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * Created by Peter Kozlovsky on 16.05.17.
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping(Routes.NEWS_URI)
    public String index(Model model) {
        model.addAttribute("news",
                newsService.findNewsByPage(1, 5));
        return Routes.NEWS_VIEW;
    }

    @GetMapping(Routes.NEWS_MORE_URI)
    @ResponseBody
    public List<News> loadMoreNews(@RequestParam("size") int size,
                                   @RequestParam("page") int page) {
        return newsService.findNewsByPage(page, size);
    }

    @GetMapping(Routes.NEWS_SHOW_URI)
    public String showNews(@PathVariable("id") Long id, Model model) {
        model.addAttribute("news", newsService.findOne(id));
        return Routes.NEWS_SHOW_VIEW;
    }

    @PostMapping(Routes.NEWS_NEW_URI)
    @PreAuthorize("hasAnyRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE,T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    public RedirectView addNews(@RequestParam("file") MultipartFile file,
                                @RequestParam("text") String text,
                                @RequestParam("title") String title) {
        try {
            News news = newsService.createNews(file, text, title);
            String path = "";
            if (news != null) {
                path = "/" + news.getId();
            }
            // redirectAttributes.addFlashAttribute("message", "Изображение будет загружено через пару секунд");
            return new RedirectView(Routes.NEWS_URI + path);
        } catch (Exception e) {
            return new RedirectView(Routes.NEWS_URI);
        }
    }


    @PostMapping(Routes.NEWS_UPDATE_URI)
    @PreAuthorize("hasAnyRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE,T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    public RedirectView updateNews(@RequestParam(value = "file", required = false) MultipartFile file,
                                   @RequestParam("text") String text,
                                   @RequestParam("title") String title,
                                   @PathVariable("id") Long id) {
        News news;
        try {
            news = newsService.updateNews(id, file, text, title);
        } catch (Exception e) {
            return new RedirectView(Routes.NEWS_URI);
        }
        return new RedirectView(Routes.NEWS_URI + "/" + news.getId());
    }

    @PostMapping(Routes.NEWS_DELETE_URI)
    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    @ResponseBody
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.delete(id);
        return "OK";
    }

    @GetMapping(value = Routes.NEWS_URI, params = {"id"})
    @PreAuthorize("hasAnyRole(T(com.code405.entity.enumeration.RoleEnumeration).EMPLOYEE,T(com.code405.entity.enumeration.RoleEnumeration).ADMIN)")
    @ResponseBody
    public News getNews(@RequestParam Long id) {
        return newsService.findOne(id);
    }
}
