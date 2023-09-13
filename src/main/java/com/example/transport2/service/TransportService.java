package com.example.transport2.service;

import com.example.transport2.mapper.TransportMapper;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;


    public Page<Transport> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return transportRepository.findAll(pageRequest);
    }

    public List<Transport> getAll() {
        return transportRepository.findAll();
    }

    public Transport getById(Integer id) {
        return transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transport with id " + id + " not found in DB."));
    }

    public List<Transport> getByLocationIdAndType(int locationId, TransportType transportType) {
        return transportRepository.findAllByLocationIdAndTypeOrderByName(locationId, transportType);
    }

    public List<Transport> getByFilters(String name, String type) {
        if (name != null && type != null) {
            return transportRepository.findByNameContainingAndType(name, TransportType.valueOf(type));
        }
        if (name != null) {
            return transportRepository.findAllByNameContaining(name);
        }
        if (type != null) {
            return transportRepository.findAllByType(TransportType.valueOf(type));
        }
        return transportRepository.findAll();

    }
}
