package com.code405.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wdtfoxs on 02.06.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JournalDisciplineDTO {
    private Long id;
    private String discipline;
}
