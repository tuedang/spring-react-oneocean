package com.oneocean.api.vessel.domain;

import lombok.*;
import org.apache.commons.lang3.Validate;

import java.time.Duration;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VesselPosition implements Comparable<VesselPosition> {
    private static final double VESSEL_SIZE_ON_COORDINATE = 0.05;

    private Instant time;
    private Vessel vessel;
    private Position position;

    @Override
    public int compareTo(VesselPosition o) {
        return time.compareTo(o.time);
    }

    public boolean isBeforeOrEqual(VesselPosition o) {
        return !time.isAfter(o.time);
    }

    public boolean isAfterOrEqual(VesselPosition o) {
        return !time.isBefore(o.time);
    }

    public boolean isBeforeOrEqual(Instant atTime) {
        return !time.isAfter(atTime);
    }

    public boolean isAfterOrEqual(Instant atTime) {
        return !time.isBefore(atTime);
    }

    public double distanceTo(VesselPosition o) {
        double y2 = o.position.getY();
        double x2 = o.position.getX();
        double y1 = position.getY();
        double x1 = position.getX();
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public Duration durationTo(VesselPosition to) {
        return Duration.between(time, to.time);
    }

    public double averageSpeedByHourTo(VesselPosition to) {
        double distance = distanceTo(to);
        Duration duration = durationTo(to);
        float numberOfHours = (float) duration.toSeconds() / 3600;
        return (float) distance / numberOfHours;
    }

    public VesselPosition atTime(VesselPosition to, Instant atTime) {
        Validate.isTrue(time.isBefore(to.time), "Wrong from/to direction for two VesselPosition");

        if (atTime.isBefore(time) || atTime.isAfter(to.time)) {
            return null;
        }
        Duration atTimeDuration = Duration.between(time, atTime);
        Duration lineDuration = durationTo(to);
        double percentage = (float) atTimeDuration.toMillis() / lineDuration.toMillis();

        double xAtTime = position.getX() + (to.position.getX() - position.getX()) * percentage;
        double yAtTime = position.getY() + (to.position.getY() - position.getY()) * percentage;

        return VesselPosition.builder()
                .position(new Position(xAtTime, yAtTime))
                .time(atTime)
                .vessel(vessel)
                .build();
    }

    public boolean isIntersectWith(VesselPosition with) {
        return distanceTo(with) < (VESSEL_SIZE_ON_COORDINATE * 2);
    }
}
