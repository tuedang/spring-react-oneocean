package com.oneocean.api.vessel.domain;

import lombok.*;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

@Getter
@Setter
public class VesselPositions {
    private final TreeSet<VesselPosition> vesselPositionsSet;

    private VesselPosition firstItem;
    private VesselPosition lastItem;

    public VesselPositions(List<VesselPosition> vessels) {
        vesselPositionsSet = new TreeSet<>(vessels);
        firstItem = vesselPositionsSet.first();
        lastItem = vesselPositionsSet.last();
    }

    public List<VesselPosition> asList() {
        return new ArrayList<>(vesselPositionsSet);
    }

    public boolean isValid() {
        if (vesselPositionsSet.isEmpty()) {
            return false;
        }
        AtomicBoolean result = new AtomicBoolean(true);
        traversal((before, after) -> {
            if (after.getTime().isBefore(before.getTime())) {
                result.set(false);
            }

            if (!Objects.equals(before.getVessel(), after.getVessel())) {
                result.set(false);
            }
            return true;
        });
        return result.get();
    }

    public void traversal(BiFunction<VesselPosition, VesselPosition, Boolean> beforeAfterFunction) {
        Iterator<VesselPosition> iterator = vesselPositionsSet.iterator();
        VesselPosition before = iterator.next();
        while (iterator.hasNext()) {
            VesselPosition after = iterator.next();
            boolean applyResult = beforeAfterFunction.apply(before, after);
            if (!applyResult) {
                break;
            }
            before = after;
        }
    }

    public double distance() {
        List<VesselPosition> vessels = asList();
        double distance = 0;
        for (int i = 0; i < vessels.size() - 1; i++) {
            distance += vessels.get(i).distanceTo(vessels.get(i + 1));
        }
        return distance;
    }

    public double averageSpeed() {
        double distance = distance();
        Duration duration = Duration.between(vesselPositionsSet.first().getTime(),
                vesselPositionsSet.last().getTime());
        float numberOfHours = (float) duration.toMinutes() / 60;
        return (float) distance / numberOfHours;
    }

    public VesselPositions splitAtTime(Instant atTime) {
        Validate.isTrue(!atTime.isBefore(firstItem.getTime()));
        Validate.isTrue(!atTime.isAfter(lastItem.getTime()));

        AtomicReference<Pair<VesselPosition, VesselPosition>> result = new AtomicReference<>();
        traversal((before, after) -> {
            if (before.isBeforeOrEqual(atTime) && after.isAfterOrEqual(atTime)) {
                result.set(Pair.of(before, after));
                return false;
            }
            return true;
        });

        Pair<VesselPosition, VesselPosition> atLine = result.get();
        Validate.notNull(atLine);
        vesselPositionsSet.add(atLine.getLeft().atTime(atLine.getRight(), atTime));
        Validate.isTrue(isValid());
        return this;
    }

}
