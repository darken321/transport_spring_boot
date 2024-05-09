package com.example.transport2.service.location;

import com.example.transport2.model.Location;
import com.example.transport2.repository.LocationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location getLocationById(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new EntityNotFoundException("location with id " + locationId + " not found in DB."));
    }

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public List<Location> findAllByNameContaining(String name) {
        return locationRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Location save(@Valid Location location) {

        if (locationRepository.existsByNameLikeIgnoreCase(location.getName())) {
            throw new EntityExistsException("Место " + location.getName() + " уже есть в базе данных");
        }
        return locationRepository.save(location);
    }

    public void delete(int id) {
        locationRepository.deleteById(id);
    }
}
