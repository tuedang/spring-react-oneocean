package com.oneocean.api.domain;

import com.oneocean.api.vessel.domain.Position;
import com.oneocean.api.vessel.domain.VesselPosition;
import com.oneocean.api.vessel.domain.VesselPositions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VesselPositionsTest {
    private ZoneId zoneId = ZoneId.systemDefault();
    VesselPosition vesselPosition1 = VesselPosition.builder()
            .position(new Position(3, 0))
            .time(ZonedDateTime.of(2023, 2, 2, 10, 10, 10, 0, zoneId)
                    .toInstant())
            .build();
    VesselPosition vesselPosition2 = VesselPosition.builder()
            .position(new Position(0, 4))
            .time(ZonedDateTime.of(2023, 2, 2, 12, 10, 10, 0, zoneId)
                    .toInstant())
            .build();

    VesselPosition vesselPosition3 = VesselPosition.builder()
            .position(new Position(6, 4))
            .time(ZonedDateTime.of(2023, 2, 2, 16, 10, 10, 0, zoneId)
                    .toInstant())
            .build();

    VesselPositions vesselPositions = new VesselPositions(List.of(vesselPosition1, vesselPosition2, vesselPosition3));

    @Test
    public void testDistance() {
        assertThat(vesselPosition1.distanceTo(vesselPosition2)).isEqualTo(5);
        assertThat(vesselPosition2.distanceTo(vesselPosition3)).isEqualTo(6);
        assertThat(vesselPositions.distance()).isEqualTo(11);
    }

    @Test
    public void testAverageSpeed() {
        // 1-2: 2 hours| 2-3: 4 hours via 11 kms
        assertThat(vesselPositions.averageSpeed()).isEqualTo((float)11/6);
    }

    @Test
    public void testSplitAtTime() {
        vesselPositions.splitAtTime(ZonedDateTime.of(2023, 2, 2, 15, 10, 10, 0, zoneId)
                .toInstant());
        List<VesselPosition> vesselPos = vesselPositions.asList();
        assertThat(vesselPositions.isValid()).isTrue();
        assertThat(vesselPos).hasSize(4);

        // split time in the middle shouldn't change the overall information
        assertThat(vesselPositions.distance()).isEqualTo(11);
        assertThat(vesselPositions.averageSpeed()).isEqualTo((float)11/6);
    }

}
