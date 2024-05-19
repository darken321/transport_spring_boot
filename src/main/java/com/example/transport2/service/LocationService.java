package com.example.transport2.service;

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

    public List<Location> findAllByNameContainingIgnoreCase(String name) {
        return locationRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Location save(@Valid Location location) {
        trimLocationFields(location);
        if (locationRepository.existsByNameLikeIgnoreCase(location.getName())) {
            throw new EntityExistsException("Место " + location.getName() + " уже есть в базе данных");
        }
        return locationRepository.save(location);
    }

    public Location update(@Valid Location location) {
        trimLocationFields(location);
        if (locationRepository.findById(location.getId()).isEmpty()) {
            throw new EntityNotFoundException("Место с id " + location.getId() + " не найдено");
        }
        return this.save(location);
    }

    public void delete(int id) {
        locationRepository.deleteById(id);
    }

    private void trimLocationFields(Location location) {
        if (location.getName() != null) location.setName(location.getName().trim());
    }
}
