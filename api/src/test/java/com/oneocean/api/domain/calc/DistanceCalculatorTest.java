package com.oneocean.api.domain.calc;

import com.oneocean.api.domain.Position;
import com.oneocean.api.domain.VesselPosition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceCalculatorTest {
    DistanceCalculator distanceCalculator = new DistanceCalculator();

    @Test
    public void testDistance() {
        VesselPosition vesselPosition1 = VesselPosition.builder()
                .position(new Position(3, 0))
                .build();
        VesselPosition vesselPosition2 = VesselPosition.builder()
                .position(new Position(0, 4))
                .build();
        assertThat(distanceCalculator.distance(vesselPosition1, vesselPosition2)).isEqualTo(5);
    }

}
