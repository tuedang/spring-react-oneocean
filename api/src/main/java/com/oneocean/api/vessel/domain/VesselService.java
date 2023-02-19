package com.oneocean.api.vessel.domain;


import com.oneocean.api.repository.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Average speed in km/h
 * Total distance travelled in km
 * Compute track intersections and warn the user when multiple vessels pass through the intersection
 * within one hour of each other
 * <p>
 * OPTIONNALY: Include a map with the corresponding ship’s routes and their associated waypoints
 * following this example:
 */
@Service
@RequiredArgsConstructor
public class VesselService {
    private final VesselRepository vesselRepository;

    public List<VesselPosition> getVesselPositions() {
        return vesselRepository.getVesselPositions();
    }

    public Map<Vessel, List<VesselPosition>> groupByVessel() {
        Map<Vessel, List<VesselPosition>> vesselPositionMap = getVesselPositions()
                .stream()
                .collect(Collectors.groupingBy(VesselPosition::getVessel, HashMap::new, Collectors.toCollection(ArrayList::new)));
        return vesselPositionMap;
    }

    public Map<Vessel, Metric> metrics() {
        Map<Vessel, List<VesselPosition>> vesselPositions = groupByVessel();

        Map<Vessel, Metric> metricMap = new HashMap<>();
        for (Vessel vessel : vesselPositions.keySet()) {
            VesselPositions vesselPositionsSet = new VesselPositions(vesselPositions.get(vessel));
            Validate.isTrue(vesselPositionsSet.isValid());
            Metric metric = Metric.builder()
                    .totalDistance(vesselPositionsSet.distance())
                    .averageSpeed(vesselPositionsSet.averageSpeed())
                    .build();
            metricMap.put(vessel, metric);
        }
        return metricMap;
    }

}
