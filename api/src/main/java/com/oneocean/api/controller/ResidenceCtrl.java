package com.oneocean.api.controller;

import com.oneocean.api.domain.Residence;
import com.oneocean.api.dto.ResidenceRequest;
import com.oneocean.api.service.ResidenceService;
import com.oneocean.api.vessel.domain.VesselService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/residence")
@RequiredArgsConstructor
public class ResidenceCtrl {

    private final ResidenceService service;
    private final VesselService vesselService;


    @GetMapping("/filterBounds")
    public List<Residence> filterBounds(@RequestParam float swLat, @RequestParam float swLng,
                                        @RequestParam float neLat, @RequestParam float neLng) {
        List<Residence> residences = service.filterBounds(swLat, swLng, neLat, neLng);
        residences.addAll(fakeVessel());
        return residences;
    }

    List<Residence> fakeVessel() {
        return vesselService.getVesselPositions().stream()
                .map(vesselPosition -> {
                    return Residence.builder()
                            .qtResidents(200)
                            .cep("1111111")
                            .id(111)
                            .longitude((float) vesselPosition.getCoordinate().getX())
                            .latitude((float) vesselPosition.getCoordinate().getY())
                            .build();
                }).collect(Collectors.toList());
    }

    @PostMapping
    public Residence create(@RequestBody @Validated ResidenceRequest residenceRequest) {
        return service.create(residenceRequest);
    }
}
