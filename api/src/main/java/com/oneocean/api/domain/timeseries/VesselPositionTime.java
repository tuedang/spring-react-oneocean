package com.oneocean.api.domain.timeseries;

import com.oneocean.api.domain.VesselPosition;

import java.time.Instant;
import java.util.List;

/**
 * Handle position at one time for ONE Vessel
 */
public class VesselPositionTime {
    private List<VesselPosition> vesselPositionData;
    private Instant min;
    private Instant max;

    public VesselPositionTime(List<VesselPosition> vesselPositionData) {
        this.vesselPositionData = vesselPositionData;
        this.min = vesselPositionData.get(0).getTime();
        this.max = vesselPositionData.get(vesselPositionData.size() - 1).getTime();
    }

    public VesselPosition atTime(Instant time) {
        if (time.isBefore(min) || time.isAfter(max)) {
            return null;
        }
        return null;
    }
}
