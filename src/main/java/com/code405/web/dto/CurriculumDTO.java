package com.code405.web.dto;

import com.code405.entity.enumeration.PassType;
import com.code405.entity.model.Curriculum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hajrullinbulat on 27.05.17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurriculumDTO {
    private Integer lectureAmount;

    private Integer practiceAmount;

    private Integer labAmount;

    private PassType passType;

    public static CurriculumDTO convert(Curriculum curriculum) {
        return CurriculumDTO.builder()
                .passType(curriculum.getPassType())
                .practiceAmount(curriculum.getPracticeAmount())
                .labAmount(curriculum.getLabAmount())
                .lectureAmount(curriculum.getLectureAmount())
                .build();
    }
}
