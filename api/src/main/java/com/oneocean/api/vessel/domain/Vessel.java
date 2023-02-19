package com.oneocean.api.vessel.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Vessel {
    private String name;

    public String toString() {
        return name;
    }
}
