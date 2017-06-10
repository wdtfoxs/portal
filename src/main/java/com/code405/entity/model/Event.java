package com.code405.entity.model;

import com.code405.entity.embeddable.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Time time;

    private String image;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Timestamp eventDate;
}
