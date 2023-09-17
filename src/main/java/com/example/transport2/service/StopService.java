package com.example.transport2.service;

import com.example.transport2.model.Stop;
import com.example.transport2.repository.StopRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {
    private final StopRepository stopRepository;

    public List<Stop> getAll() {
        return stopRepository.findAll();
    }

    public List<Stop> findAllByNameContaining(String name) {
        return stopRepository.findAllByNameContaining(name);
    }

    public Stop getById(Integer id) {
        return stopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("stop with id " + id + " not found in DB"));
    }

    public Stop save(Stop stop) {
        return stopRepository.save(stop);
    }

    public void delete(int id) {
        stopRepository.deleteById(id);
    }
}
