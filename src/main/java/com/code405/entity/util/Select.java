package com.code405.entity.util;

import lombok.*;

/**
 * Created by Peter Kozlovsky on 28.05.17.
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Select {
    private boolean selected;
    private String value;
    private String text;
    private boolean select;
}
