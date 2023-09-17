package com.example.transport2.service;

import com.example.transport2.model.Stop;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.repository.StopRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public String getLocationById(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new EntityNotFoundException("location with id " + locationId + " not found in DB.")).getName();
    }
}
