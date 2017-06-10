package com.code405.repository.models;

import com.code405.entity.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findFirst3ByOrderByCreatedDesc();
    News findFirst1ByOrderByCreatedDesc();
}
