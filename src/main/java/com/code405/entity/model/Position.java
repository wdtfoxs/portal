package com.code405.entity.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wdtfoxs on 20.05.2017.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "position")
public class Position implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "position", nullable = false, unique = true)
    private String position;

}
