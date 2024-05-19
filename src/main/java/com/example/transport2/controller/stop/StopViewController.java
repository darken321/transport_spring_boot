package com.example.transport2.controller.stop;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.location.LocationViewDto;
import com.example.transport2.dto.stop.StopEditDto;
import com.example.transport2.dto.stop.StopSaveDto;
import com.example.transport2.mapper.LocationMapper;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.service.LocationService;
import com.example.transport2.service.StopService;
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
@RequestMapping(value = StopViewController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)

public class StopViewController {

    static final String REST_URL = "/stops";

    private final StopService stopService;
    private final StopMapper stopMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;


    @GetMapping("/table")
    public String getAllStopsTable(@RequestParam(required = false) String name, Model model) {
        List<StopDto> stops;
        List<LocationViewDto> locations;
        if (name != null && !name.isEmpty()) {
            stops = stopMapper.toDto(stopService.findAllByNameContaining(name.trim()));
        } else {
            stops = stopMapper.toDto(stopService.getAll());
        }
        locations = locationMapper.toDto(locationService.getAll());
        model.addAttribute("stops", stops);
        model.addAttribute("locations", locations);
        return "stop-CRUD";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        StopDto stopDto = stopMapper.toDto(stopService.getById(id));
        List<Location> locations = locationService.getAll();
        model.addAttribute("locations", locations);
        model.addAttribute("stop", stopDto);
        return "edit-stop";
    }

    @PostMapping("/update")
    public String updateStop(@ModelAttribute @Valid StopEditDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        stopService.update(stop);
        return "redirect:/stops/table";
    }

    @PostMapping("/deleteStop/{id}")
    public String deleteStop(@PathVariable int id) {
        stopService.delete(id);
        return "redirect:/stops/table"; // Перенаправление обратно на страницу управления данными
    }

    @PostMapping("/add")
    public String save(@ModelAttribute @Valid StopSaveDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        Stop save = stopService.save(stop);
        return "redirect:/stops/table"; // Перенаправление обратно на страницу управления данными
    }
}