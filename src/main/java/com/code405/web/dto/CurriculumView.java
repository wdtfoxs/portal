package com.code405.web.dto;

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
public class CurriculumView {
    private String name;
    private CurriculumDTO first;
    private CurriculumDTO second;
    private Integer total;
}
