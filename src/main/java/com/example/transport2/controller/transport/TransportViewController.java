package com.example.transport2.controller.transport;

import com.example.transport2.dto.transport.TransportDto;
import com.example.transport2.dto.transport.TransportEditDto;
import com.example.transport2.dto.transport.TransportSaveDto;
import com.example.transport2.mapper.TransportMapper;
import com.example.transport2.model.Transport;
import com.example.transport2.service.TransportService;
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
@RequestMapping(value = TransportViewController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)

public class TransportViewController {

    static final String REST_URL = "/transports";

    private final TransportService transportService;
    private final TransportMapper transportMapper;

    @GetMapping("/table")
    public String getAllTransportsTable(@RequestParam(required = false) String name, Model model) {
        List<TransportDto> transports;
        if (name != null && !name.isEmpty()) {
            transports = transportMapper.toDto(transportService.findAllByNameContaining(name.trim()));
        } else {
            transports = transportMapper.toDto(transportService.getAll());
        }
        model.addAttribute("transports", transports);
        return "transport-CRUD";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        TransportDto transportDto = transportMapper.toDto(transportService.getById(id));
        model.addAttribute("transport", transportDto);
        return "edit-transport";
    }
    @PostMapping("/update")
    public String updateTransport(@ModelAttribute @Valid TransportEditDto dto) {
//        DtoUtils.trimNameLocationComment(dto);
        Transport transport = transportMapper.fromDto(dto);
        transportService.update(transport);
        return "redirect:/transports/table";
    }

    @PostMapping("/deleteTransport/{id}")
    public String deleteTransport(@PathVariable int id) {
        transportService.delete(id);
        return "redirect:/transports/table"; // Перенаправление обратно на страницу управления данными
    }

    @PostMapping("/add")
    public String save(@ModelAttribute @Valid TransportSaveDto dto) {
//        DtoUtils.trimNameLocationComment(dto);
        Transport transport = transportMapper.fromDto(dto);
        Transport save = transportService.save(transport);
        return "redirect:/transports/table"; // Перенаправление обратно на страницу управления данными
    }
}