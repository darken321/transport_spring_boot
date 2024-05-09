package com.example.transport2.controller.stop;

import com.example.transport2.dto.stop.StopDto;
import com.example.transport2.dto.stop.StopEditDto;
import com.example.transport2.dto.stop.StopSaveDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.service.stop.StopService;
import com.example.transport2.util.DtoUtils;
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

    @GetMapping("/table")
    public String getAllStopsTable(@RequestParam(required = false) String name, Model model) {
        List<StopDto> stops;
        if (name != null && !name.isEmpty()) {
            stops = stopMapper.toDto(stopService.findAllByNameContaining(name.trim()));
        } else {
            stops = stopMapper.toDto(stopService.getAll());
        }
        model.addAttribute("stops", stops);
        return "stop-CRUD";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        StopDto stopDto = stopMapper.toDto(stopService.getById(id));
        model.addAttribute("stop", stopDto);
        return "edit-stop";
    }
    @PostMapping("/update")
    public String updateStop(@ModelAttribute @Valid StopEditDto dto) {
        dto = DtoUtils.trimNameAndLocation(dto);
        Stop stop = stopMapper.fromDto(dto);
        stopService.save(stop);
        return "redirect:/stops/table";
    }

    @PostMapping("/deleteStop/{id}")
    public String deleteStop(@PathVariable int id) {
        stopService.delete(id);
        return "redirect:/stops/table"; // Перенаправление обратно на страницу управления данными
    }

    @PostMapping("/add")
    public String save(@ModelAttribute @Valid StopSaveDto dto) {
        dto = DtoUtils.trimNameAndLocation(dto);
        Stop stop = stopMapper.fromDto(dto);
        Stop save = stopService.save(stop);
        return "redirect:/stops/table"; // Перенаправление обратно на страницу управления данными
    }
}