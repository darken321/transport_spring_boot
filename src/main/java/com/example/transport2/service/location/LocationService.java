package com.example.transport2.service.location;

import com.example.transport2.model.Location;
import com.example.transport2.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location getLocationById(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new EntityNotFoundException("location with id " + locationId + " not found in DB."));
    }
}
