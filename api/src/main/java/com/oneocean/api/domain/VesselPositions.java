package com.oneocean.api.domain;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@NoArgsConstructor
@Getter
@Setter
public class VesselPositions {
    Map<Vessel, TreeSet<VesselPosition>> vesselPositionsMap;
    public VesselPositions(Map<Vessel, List<VesselPosition>> positionMap) {
        vesselPositionsMap = new HashMap<>();
        for(Vessel vessel: positionMap.keySet()) {
            vesselPositionsMap.put(vessel, new TreeSet<>(positionMap.get(vessel)));
        }
    }


}
