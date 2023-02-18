package com.oneocean.api.domain;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VesselPosition implements Comparable<VesselPosition> {
    private Instant time;
    private Vessel vessel;
    private Position position;

    @Override
    public int compareTo(VesselPosition o) {
        return time.compareTo(o.time);
    }

}
