package com.oneocean.api.domain.calc;

import com.oneocean.api.domain.VesselPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeSet;

public class SpeedCalculator {
    private DistanceCalculator distanceCalculator = new DistanceCalculator();

    public BigDecimal averageSpeed(TreeSet<VesselPosition> vesselPositions) {
        BigDecimal distance = distanceCalculator.calculate(vesselPositions);

        Instant first = vesselPositions.first().getTime();
        Instant last = vesselPositions.last().getTime();
        Duration duration = Duration.between(first, last);
        float numberOfHours = (float) duration.toMinutes() / 60;
        return distance.divide(BigDecimal.valueOf(numberOfHours), 2, RoundingMode.HALF_UP);
    }
}
