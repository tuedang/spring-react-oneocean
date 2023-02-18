package com.oneocean.api.service;

import com.oneocean.api.domain.Residence;
import com.oneocean.api.dto.ResidenceRequest;
import com.oneocean.api.repository.ResidenceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidenceService {

    private final ResidenceRepository repository;

    public ResidenceService(ResidenceRepository repository) {
        this.repository = repository;
    }

    public List<Residence> filterBounds(float swLat, float swLng, float neLat, float neLng) {
        return repository.filterByBounds(swLat, swLng, neLat, neLng);
    }

    public Residence create(ResidenceRequest residenceRequest) {
        Residence residence = new Residence();
        BeanUtils.copyProperties(residenceRequest, residence);
        return repository.save(residence);
    }
}
