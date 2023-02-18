package com.oneocean.api.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class VesselPositionTest {
    VesselPosition vesselPosition1 = VesselPosition.builder()
            .position(new Position(3, 0))
            .time(ZonedDateTime.of(2023, 2, 2, 10, 10, 10, 0, ZoneId.systemDefault())
                    .toInstant())
            .build();
    VesselPosition vesselPosition2 = VesselPosition.builder()
            .position(new Position(0, 4))
            .time(ZonedDateTime.of(2023, 2, 2, 12, 10, 10, 0, ZoneId.systemDefault())
                    .toInstant())
            .build();

    @Test
    public void testDistanceTo() {
        assertThat(vesselPosition1.distanceTo(vesselPosition2)).isEqualTo(5);
        assertThat(vesselPosition2.distanceTo(vesselPosition1)).isEqualTo(5);
    }

    @Test
    public void testDurationTo() {
        Duration duration = vesselPosition1.durationTo(vesselPosition2);
        assertThat(duration.toHours()).isEqualTo(2);

        Duration duration2 = vesselPosition2.durationTo(vesselPosition1);
        assertThat(duration2.toHours()).isEqualTo(-2);
    }

    @Test
    public void testAverageSpeedByHourTo() {
        double averageSpeedByHour = vesselPosition1.averageSpeedByHourTo(vesselPosition2);
        assertThat(averageSpeedByHour).isEqualTo(2.5);
    }

    @Test
    public void testAtTime() {
        Instant atTime = vesselPosition1.getTime().plus(1, ChronoUnit.HOURS);
        VesselPosition vesselPosition = vesselPosition1.atTime(vesselPosition2, atTime);
        assertThat(vesselPosition.getPosition().getX()).isEqualTo(1.5);
        assertThat(vesselPosition.getPosition().getY()).isEqualTo(2);

        Instant atTime30Mins = vesselPosition1.getTime().plus(30, ChronoUnit.MINUTES);
        VesselPosition vesselPosition30Mins = vesselPosition1.atTime(vesselPosition2, atTime30Mins);
        assertThat(vesselPosition30Mins.getPosition().getX()).isEqualTo(2.25);
        assertThat(vesselPosition30Mins.getPosition().getY()).isEqualTo(1);

        Instant atTimeAfter2H = vesselPosition1.getTime().plus(2, ChronoUnit.HOURS);
        VesselPosition atPosition2H = vesselPosition1.atTime(vesselPosition2, atTimeAfter2H);
        assertThat(atPosition2H.getPosition().getX()).isEqualTo(vesselPosition2.getPosition().getX());
        assertThat(atPosition2H.getPosition().getY()).isEqualTo(vesselPosition2.getPosition().getY());

        Instant atTimeAfter3H = vesselPosition1.getTime().plus(3, ChronoUnit.HOURS);
        VesselPosition atPosition3H = vesselPosition1.atTime(vesselPosition2, atTimeAfter3H);
        assertThat(atPosition3H).isNull();
    }
}
