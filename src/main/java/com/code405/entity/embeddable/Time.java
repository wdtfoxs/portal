package com.code405.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Time {
    @Column(name = "date_from")
    private Timestamp from;
    @Column(name = "date_to")
    private Timestamp to;
}
