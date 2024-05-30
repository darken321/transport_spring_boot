package com.example.transport2.service;

import com.example.transport2.model.*;
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
        trimTransportFields(transport);
        // Проверяем, существует ли уже транспорт такого типа с таким же именем в том же городе
        boolean transportExists = transportRepository.existsByNameIgnoreCaseAndType(
                transport.getName(),
//                transport.getLocation(),
                transport.getType());

        if (transportExists) {
            throw new EntityExistsException("Такой транспорт " + transport.getName() + " уже есть");
        }
        // Если транспортов с таким именем в этом городе пуст, таких не найдено, сохраняем новую остановку
        return transportRepository.save(transport);
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

    public List<Transport> getByLocationIdAndType(TransportType transportType) {
        return transportRepository.findAllByTypeOrderByName(transportType);
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
        trimTransportFields(transport);
        //нужно изменить поля конкретного транспорта, у него есть ID

        // Получаем существующий транспорт по ID или выбрасываем исключение, если он не найден
        Transport existingTransport = transportRepository.findById(transport.getId())
                .orElseThrow(() -> new EntityNotFoundException("Транспорт с id " + transport.getId() + " не найден"));

        // Проверить что транспорта с таким именем такого типа нет в городе назначения
        // Проверяем, существует ли другой транспорт с таким же именем, местоположением и типом, но с другим ID
        List<Transport> conflictingTransports = transportRepository.findByNameIgnoreCaseAndType(
                        transport.getName(),
//                        transport.getLocation(),
                        transport.getType())
                .stream()
                .filter(t -> !t.getId().equals(transport.getId()))
                .toList();

        // Если найден конфликтующий транспорт, выбрасываем исключение
        if (!conflictingTransports.isEmpty()) {
            throw new EntityExistsException("Такой транспорт " + transport.getName() + " уже есть");
        }
        // Обновляем поля существующего транспорта данными из полученного транспорта
        existingTransport.setName(transport.getName());
        existingTransport.setType(transport.getType());
//        existingTransport.setLocation(transport.getLocation());
        existingTransport.setComment(transport.getComment());

        // Сохраняем обновленный транспорт
        return transportRepository.save(existingTransport);
    }

    public void delete(int id) {
        transportRepository.deleteById(id);
    }

    public Transport getTransportById(Integer id) {
        return transportRepository.getTransportById(id);
    }
    private void trimTransportFields(Transport transport) {
        if (transport.getName() != null) {
            transport.setName(transport.getName().trim());
        }
       if (transport.getComment() != null) {
           transport.setComment(transport.getComment().trim());
       }
    }
}