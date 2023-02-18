package com.oneocean.api.domain.timeseries;

import com.oneocean.api.domain.VesselPosition;

import java.time.Instant;
import java.util.TreeMap;

public class VesselMapTimeSeries {
    TreeMap<Instant, VesselPosition> vesselPositionList;

}
