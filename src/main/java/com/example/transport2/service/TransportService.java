package com.example.transport2.service;

import com.example.transport2.model.Transport;
import com.example.transport2.repository.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;

    public Page<Transport> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return transportRepository.findAll(pageRequest);
    }

    public Transport getById(Integer id) {
        return transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transport with id " + id + " not found in DB."));
    }

}
