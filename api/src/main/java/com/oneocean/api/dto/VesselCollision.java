package com.oneocean.api.dto;

import com.oneocean.api.vessel.domain.Position;
import com.oneocean.api.vessel.domain.Vessel;
import com.oneocean.api.vessel.domain.VesselPosition;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VesselCollision {
    private VesselPosition vesselPosition1;
    private VesselPosition vesselPosition2;

    private VesselPosition centerPosition;

    public VesselCollision summary() {
        long total = vesselPosition1.getTime().toEpochMilli() + vesselPosition2.getTime().toEpochMilli();
        centerPosition = VesselPosition.builder()
                .vessel(new Vessel(String.format("%s-%s", vesselPosition1.getVessel().getName(), vesselPosition2.getVessel().getName())))
                .time(Instant.ofEpochMilli(total/2))
                .position(Position.builder()
                        .x((vesselPosition1.getPosition().getX()+ vesselPosition2.getPosition().getX())/2)
                        .y((vesselPosition1.getPosition().getY()+ vesselPosition2.getPosition().getY())/2)
                        .build())
                .build();
        return this;
    }
}
