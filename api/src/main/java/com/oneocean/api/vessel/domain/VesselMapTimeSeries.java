package com.oneocean.api.vessel.domain;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class VesselMapTimeSeries {
    private List<VesselPositions> vesselPositionsList;

    public VesselMapTimeSeries(List<VesselPosition> allVessels) {
        Map<Vessel, List<VesselPosition>> vesselsMap = partitionByVessel(allVessels);
        vesselPositionsList = vesselsMap.values().stream()
                .map(VesselPositions::new)
                .collect(Collectors.toList());
    }

    public Map<Vessel, List<VesselPosition>> partitionByVessel(List<VesselPosition> allVessels) {
        Map<Vessel, List<VesselPosition>> vesselPositionMap = allVessels
                .stream()
                .collect(Collectors.groupingBy(VesselPosition::getVessel, HashMap::new, Collectors.toCollection(ArrayList::new)));
        return vesselPositionMap;
    }

    public Instant getStartTime() {
        return vesselPositionsList.stream()
                .map(vesselPositions -> vesselPositions.getFirstItem().getTime())
                .min(Instant::compareTo)
                .orElse(null);
    }

    public Instant getEndTime() {
        return vesselPositionsList.stream()
                .map(vesselPositions -> vesselPositions.getLastItem().getTime())
                .max(Instant::compareTo)
                .orElse(null);
    }

    public List<VesselPosition> move(Instant instant, Duration duration) {
        Instant toTime = instant.plus(duration.toSeconds(), ChronoUnit.SECONDS);
        return vesselPositionsList.stream()
                .filter(vesselPositions -> vesselPositions.canSplitAt(toTime))
                .map(vesselPositions -> vesselPositions.splitAtTime(toTime).getKey())
                .collect(Collectors.toList());
    }

    public List<Pair<VesselPosition, VesselPosition>> intersect(List<VesselPosition> vesselPositions) {
        List<Pair<VesselPosition, VesselPosition>> intersectsList = new ArrayList<>();
        for (int i = 0; i < vesselPositions.size() - 1; i++) {
            if (vesselPositions.get(i).isIntersectWith(vesselPositions.get(i + 1))) {
                intersectsList.add(Pair.of(vesselPositions.get(i), vesselPositions.get(i + 1)));
            }
        }
        return intersectsList;
    }

}
