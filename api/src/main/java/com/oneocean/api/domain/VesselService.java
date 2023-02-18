package com.oneocean.api.domain;


import com.oneocean.api.domain.calc.DistanceCalculator;
import com.oneocean.api.domain.calc.SpeedCalculator;
import com.oneocean.api.repository.VesselRepository;
import lombok.RequiredArgsConstructor;
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
    DistanceCalculator distanceCalculator = new DistanceCalculator();
    SpeedCalculator speedCalculator = new SpeedCalculator();

    public List<VesselPosition> getVesselPositions() {
        return vesselRepository.getVesselPositions();
    }

    public VesselPositions groupByVessel() {
        Map<Vessel, List<VesselPosition>> vesselPositionMap = getVesselPositions()
                .stream()
                .collect(Collectors.groupingBy(VesselPosition::getVessel, HashMap::new, Collectors.toCollection(ArrayList::new)));
        return new VesselPositions(vesselPositionMap);
    }

    public Map<Vessel, Metric> metrics() {
        VesselPositions vesselPositions = groupByVessel();

        Map<Vessel, Metric> metricMap = new HashMap<>();
        for (Vessel vessel : vesselPositions.getVesselPositionsMap().keySet()) {
            TreeSet<VesselPosition> vesselPos = vesselPositions.getVesselPositionsMap().get(vessel);
            Metric metric = Metric.builder()
                    .totalDistance(distanceCalculator.calculate(vesselPos).doubleValue())
                    .averageSpeed(speedCalculator.averageSpeed(vesselPos).doubleValue())
                    .build();
            metricMap.put(vessel, metric);
        }
        return metricMap;
    }

}
