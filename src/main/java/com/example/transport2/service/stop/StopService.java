package com.example.transport2.service.stop;

import com.example.transport2.model.Stop;
import com.example.transport2.repository.StopRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
        return stopRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Stop getById(Integer id) {
        return stopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("stop with id " + id + " not found in DB"));
    }

    public Stop save(@Valid Stop stop) {

        if (stopRepository.existsByNameLikeIgnoreCase(stop.getName())) {
            throw new EntityExistsException("Остановка " + stop.getName() + " уже есть в базе данных");
        }
        return stopRepository.save(stop);
    }

    public void delete(int id) {
        stopRepository.deleteById(id);
    }
}