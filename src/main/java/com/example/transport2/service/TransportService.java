package com.example.transport2.service;

import com.example.transport2.model.RouteStops;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.RouteStopRepository;
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
    private final RouteStopRepository routeStopRepository;


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

    public List<Transport> getByFilters(String name, TransportType transportType) {
        if (name != null && transportType != null) {
            return transportRepository.findByNameContainingAndType(name, transportType);
        }
        if (name != null) {
            return transportRepository.findAllByNameContaining(name);
        }
        if (transportType != null) {
            return transportRepository.findAllByType(transportType);
        }
        return transportRepository.findAll();
    }

    public List<Transport> getByStopId(Integer stopId) {
//        return routeStopRepository.findAllByStopId(stopId).stream()
//                .map(RouteStops::getRoute)
//                .map(TransportRoute::getTransport)
//                .distinct()
//                .toList();

        List<RouteStops> allByStopId = routeStopRepository.findAllByStopId(stopId);

        List<TransportRoute> transportRoutes = allByStopId.stream()
                .map(RouteStops::getRoute)
                .toList();

        List<Transport> transports = transportRoutes.stream()
                .map(TransportRoute::getTransport)
                .distinct()
                .toList();
        return transports;

    }
}
