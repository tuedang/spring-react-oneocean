package com.oneocean.api.controller;

import com.oneocean.api.domain.Residence;
import com.oneocean.api.dto.ResidenceRequest;
import com.oneocean.api.service.ResidenceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/residence")
public class ResidenceCtrl {

    private final ResidenceService service;

    public ResidenceCtrl(ResidenceService service) {
        this.service = service;
    }

    @GetMapping("/filterBounds")
    public List<Residence> filterBounds(@RequestParam float swLat, @RequestParam float swLng,
                                        @RequestParam float neLat, @RequestParam float neLng) {
        return service.filterBounds(swLat, swLng, neLat, neLng);
    }

    @PostMapping
    public Residence create(@RequestBody @Validated ResidenceRequest residenceRequest) {
        return service.create(residenceRequest);
    }
}
