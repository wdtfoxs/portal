package com.code405.web.controller.rest;

import com.code405.entity.model.Event;
import com.code405.entity.model.News;
import com.code405.repository.models.EventRepository;
import com.code405.repository.models.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Peter Kozlovsky on 01.06.17.
 */
@RequestMapping("/count")
@RestController
public class CounterController {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/news")
    public Long countNews() {
        return newsRepository.count();
    }

    @GetMapping("/event")
    public Long countEvent() {
        return eventRepository.count();
    }

    @GetMapping("/news/get")
    public News getNews(){
        return newsRepository.findFirst1ByOrderByCreatedDesc();
    }

    @GetMapping("/event/get")
    public Event getEvent(){
        return eventRepository.findFirst1ByOrderByEventDateDesc();
    }
}
