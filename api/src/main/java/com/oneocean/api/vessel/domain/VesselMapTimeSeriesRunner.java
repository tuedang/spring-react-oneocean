package com.oneocean.api.vessel.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VesselMapTimeSeriesRunner {
    VesselMapTimeSeries vesselMapTimeSeries;

    public VesselMapTimeSeriesRunner(List<VesselPosition> allVessels) {
        vesselMapTimeSeries = new VesselMapTimeSeries(allVessels);
    }

    public List<Pair<VesselPosition, VesselPosition>> checkMovingCollision(Duration duration) {
        log.info("Start collision checking");
        List<Pair<VesselPosition, VesselPosition>> allCollisions = new ArrayList<>();
        Instant startTime = vesselMapTimeSeries.getStartTime();
        Instant endTime = vesselMapTimeSeries.getEndTime();

        Instant moveAtTime = startTime;
        while (moveAtTime.isBefore(endTime)) {
            log.info("Check at time {}", moveAtTime);

            List<VesselPosition> vesselPositions = vesselMapTimeSeries.move(moveAtTime, duration);
            List<Pair<VesselPosition, VesselPosition>> collisions = vesselMapTimeSeries.intersect(vesselPositions);

            if (!collisions.isEmpty()) {
                for (Pair<VesselPosition, VesselPosition> pair : collisions) {
                    log.warn("Collision at {}/{}", pair.getLeft(), pair.getRight());
                }
                allCollisions.addAll(collisions);
            }
            moveAtTime = moveAtTime.plus(duration.toMillis(), ChronoUnit.MILLIS);
        }
        log.info("End collision checking");
        return deduplicateCollision(allCollisions);
    }

    private List<Pair<VesselPosition, VesselPosition>> deduplicateCollision(List<Pair<VesselPosition, VesselPosition>> collisions) {
        if (collisions.size() <= 1) {
            return collisions;
        }

        List<Pair<VesselPosition, VesselPosition>> duplicatedPositions = new ArrayList<>();
        for (int i = 0; i < collisions.size() - 1; i++) {
            if (isSimilar(collisions.get(i), collisions.get(i + 1))) {
                duplicatedPositions.add(collisions.get(i));
            }
        }
        collisions.removeAll(duplicatedPositions);
        return collisions;
    }

    /**
     * Because VesselPosition has SIZE, then it can be overlapping together by very short period checking
     * @param pair1 first cutting point
     * @param pair2 second cutting point
     * @return true if two points are similar and close to each other
     */
    private boolean isSimilar(Pair<VesselPosition, VesselPosition> pair1, Pair<VesselPosition, VesselPosition> pair2) {
        VesselPosition vesselPositionK1 = pair1.getKey();
        VesselPosition vesselPositionV1 = pair1.getValue();
        VesselPosition vesselPositionK2 = pair2.getKey();
        VesselPosition vesselPositionV2 = pair2.getValue();

        return vesselPositionK1.getVessel().equals(vesselPositionK2.getVessel()) && vesselPositionK1.isIntersectWith(vesselPositionK2)
            && vesselPositionV1.getVessel().equals(vesselPositionV2.getVessel()) && vesselPositionV1.isIntersectWith(vesselPositionV2);
    }

}
