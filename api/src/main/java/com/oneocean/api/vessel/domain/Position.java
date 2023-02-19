package com.oneocean.api.vessel.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class Position {
    private double x;
    private double y;
}
