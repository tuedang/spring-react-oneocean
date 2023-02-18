package com.oneocean.api.domain.calc;

import com.oneocean.api.domain.VesselPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DistanceCalculator {
    public BigDecimal calculate(TreeSet<VesselPosition> vesselPositions) {
        List<VesselPosition> vessels = new ArrayList<>(vesselPositions);
        double distance = 0;
        for (int i = 0; i < vessels.size() - 1; i++) {
            distance += distance(vessels.get(i), vessels.get(i + 1));
        }
        return BigDecimal.valueOf(distance)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public double distance(VesselPosition vesselPosition1, VesselPosition vesselPosition2) {
        int y2 = vesselPosition2.getPosition().getY();
        int x2 = vesselPosition2.getPosition().getX();
        int y1 = vesselPosition1.getPosition().getY();
        int x1 = vesselPosition1.getPosition().getX();
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
