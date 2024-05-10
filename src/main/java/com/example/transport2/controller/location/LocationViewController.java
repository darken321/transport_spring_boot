package com.example.transport2.controller.location;

import com.example.transport2.dto.location.LocationDto;
import com.example.transport2.dto.location.LocationEditDto;
import com.example.transport2.dto.location.LocationSaveDto;
import com.example.transport2.mapper.LocationMapper;
import com.example.transport2.model.Location;
import com.example.transport2.service.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = LocationViewController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)

public class LocationViewController {

    static final String REST_URL = "/locations";

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @GetMapping("/location")
    public String getAllLocationsTable(@RequestParam(required = false) String name, Model model) {
        List<LocationDto> locations;
        if (name != null && !name.isEmpty()) {
            locations = locationMapper.toDto(locationService.findAllByNameContaining(name.trim()));
        } else {
            locations = locationMapper.toDto(locationService.getAll());
        }
        model.addAttribute("locations", locations);
        return "location-CRUD";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        LocationDto dto = locationMapper.toDto(locationService.getLocationById(id));
        model.addAttribute("location", dto);
        return "edit-location";
    }
    @PostMapping("/update")
    public String updateLocation(@ModelAttribute @Valid LocationEditDto dto) {
        dto.setName(dto.getName().trim());
        Location location = locationMapper.fromDto(dto);
        locationService.save(location);
        return "redirect:/locations/location";
    }

    @PostMapping("/deleteLocation/{id}")
    public String deleteStop(@PathVariable int id) {
        locationService.delete(id);
        return "redirect:/locations/location"; // Перенаправление обратно на страницу управления данными
    }

    @PostMapping("/add")
    public String save(@ModelAttribute @Valid LocationSaveDto dto) {
        dto.setName(dto.getName().trim());
        Location location = locationMapper.fromDto(dto);
        Location save = locationService.save(location);
        return "redirect:/locations/location"; // Перенаправление обратно на страницу управления данными
    }
}