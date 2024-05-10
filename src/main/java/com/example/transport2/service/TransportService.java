package com.example.transport2.service;

import com.example.transport2.model.RouteStops;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.model.TransportType;
import com.example.transport2.projection.TransportInfo;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.TransportRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    public Transport save(@Valid Transport transport) {
        // Проверяем, существует ли уже транспорт такого типа с таким же именем в том же городе
        List<Transport> existingTransports = transportRepository.findByNameIgnoreCaseAndLocationAndType(
                transport.getName(),
                transport.getLocation(),
                transport.getType());

        if (existingTransports.isEmpty()) {
            // Если транспортов с таким именем в этом городе пуст, таких не найдено, сохраняем новую остановку
            return transportRepository.save(transport);
        }
        throw new EntityExistsException("Такой транспорт " + transport.getName() + " уже есть");
    }

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
        return routeStopRepository.findAllByStopId(stopId).stream()
                .map(RouteStops::getRoute)
                .map(TransportRoute::getTransport)
                .distinct()
                .toList();
    }

    public TransportInfo getTransportInfoById(Integer transportId) {
        return transportRepository.findTransportInfoById(transportId);
    }

    public List<Transport> findAllByNameContaining(String name) {
        return transportRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Transport update(@Valid Transport transport) {

        if (transportRepository.existsById(transport.getId())) { //если есть транспорт с таким ID

            //проверить что транспорта такого типа нет в городе назначения
            //берем список всех транспортов с таким именем, в таком городе, такого типа
            List<Transport> existingTransports = transportRepository.findByNameIgnoreCaseAndLocationAndType(
                    transport.getName(),
                    transport.getLocation(),
                    transport.getType());
            //если таких нет, список пуст то сохраняем
            if (existingTransports.isEmpty() //такого нет
                    ||!existingTransports.get(0).equals(transport) //??
                    ||existingTransports.get(0).getId().equals(transport.getId() //тот же транспорт с тем же ID
            ) ) {
                return transportRepository.save(transport);
            }
//            return this.save(transport);
            throw new EntityExistsException("Такой транспорт " + transport + " уже есть");
        }
        throw new EntityNotFoundException("Транспорт с id " + transport.getId() + " не найден");
    }

    public void delete(int id) {
        transportRepository.deleteById(id);
    }
}