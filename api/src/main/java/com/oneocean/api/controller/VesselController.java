package com.oneocean.api.controller;

import com.oneocean.api.dto.VesselCollision;
import com.oneocean.api.vessel.domain.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Map<Vessel, List<VesselPosition>> getVesselGroup() {
        return vesselService.groupByVessel();
    }

    @GetMapping("/metric")
    public Map<Vessel, Metric> metric() {
        return vesselService.metrics();
    }

    @GetMapping("/collision")
    public List<VesselCollision> collision() {
        List<Pair<VesselPosition, VesselPosition>> collisions = vesselService.checkCollision();
        return collisions.stream()
                .map(pair -> VesselCollision.builder()
                        .vesselPosition1(pair.getLeft())
                        .vesselPosition2(pair.getRight())
                        .build()
                        .summary())
                .collect(Collectors.toList());

    }
}
