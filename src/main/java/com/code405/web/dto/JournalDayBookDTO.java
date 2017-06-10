package com.code405.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wdtfoxs on 01.06.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JournalDayBookDTO {
    private String group;
    private Long student;
    private Long discipline;
    private String date;
    private boolean presence;
    private Integer points;
}
