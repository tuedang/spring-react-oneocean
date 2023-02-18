package com.oneocean.api.controller;

import com.oneocean.api.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/vessels")
@AllArgsConstructor
public class VesselController {
    private final VesselService vesselService;

    @GetMapping("/")
    public List<VesselPosition> getVesselPositions() {
        return vesselService.getVesselPositions();
    }

    @GetMapping("/group")
    public VesselPositions getVesselGroup() {
        return vesselService.groupByVessel();
    }

    @GetMapping("/metric")
    public Map<Vessel, Metric> metric() {
        return vesselService.metrics();
    }
}
