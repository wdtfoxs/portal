package com.code405.web.dto;

import com.code405.entity.embeddable.Time;
import com.code405.entity.model.Discipline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hajrullinbulat on 20.05.17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDTO {
    private Discipline discipline;
    private Time passTime;
    private Integer semesterPoints;
    private Integer examPoints;
    private Integer totalPoints;
    private Double averageGroupPoints;
    private Double averageCoursePoints;
}
