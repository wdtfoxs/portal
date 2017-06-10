package com.code405.repository.models;

import com.code405.entity.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findFirst3ByOrderByEventDateDesc();
    Event findFirst1ByOrderByEventDateDesc();
}
