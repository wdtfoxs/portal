package com.code405.repository.models;

import com.code405.entity.model.Auditorium;
import com.code405.entity.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
