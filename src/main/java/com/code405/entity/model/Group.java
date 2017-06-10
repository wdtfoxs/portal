package com.code405.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "groups")
public class Group implements Serializable {
    @Id
    @Column(name = "group_id", nullable = false)
    private String groupCode;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

}
