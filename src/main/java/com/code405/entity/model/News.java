package com.code405.entity.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "news")
public class News {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String image;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, length = -1)
    private String text;

    @Column(nullable = false)
    private Timestamp created;
}
