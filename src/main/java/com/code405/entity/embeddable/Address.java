package com.code405.entity.embeddable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
}
