package com.oneocean.api.vessel.domain;

import com.oneocean.api.vessel.domain.VesselPosition;

import java.time.Instant;
import java.util.TreeMap;

public class VesselMapTimeSeries {
    TreeMap<Instant, VesselPosition> vesselPositionList;

}
