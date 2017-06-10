package com.code405.web.dto;

import com.code405.entity.model.Reference;
import lombok.*;

import java.util.List;

/**
 * Created by wdtfoxs on 17.05.2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class CabinetDTO {

    private String name;
    private String surname;
    private String groupCode;
    private String speciality;
    private List<Reference> references;
}
