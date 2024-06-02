package com.example.transport2.controller.route;

import com.example.transport2.dto.FullRouteInfoDto;
import com.example.transport2.dto.RouteInfoDto;
import com.example.transport2.dto.route.RouteViewDto;
import com.example.transport2.mapper.LocationMapper;
import com.example.transport2.mapper.RouteMapper;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.service.LocationService;
import com.example.transport2.service.RouteService;
import com.example.transport2.service.StopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = RouteViewController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RouteViewController {

    static final String REST_URL = "/route";

    private final StopService stopService;
    private final StopMapper stopMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    // собираем данные одного маршрута
    @GetMapping("/table")
    public String getAllRoutesTable(@RequestParam(required = true) Integer routeId, Model model) {
        FullRouteInfoDto routeInfo = routeService.findById(routeId);;
        List<RouteViewDto> routeStops;

            routeStops = routeService.findAllById(routeId);

        model.addAttribute("routeStops", routeStops);
        model.addAttribute("routeInfo", routeInfo);
        return "route-CRUD";
    }

//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") int id, Model model) {
//        StopDto stopDto = stopMapper.toDto(stopService.getById(id));
//        List<Location> locations = locationService.getAll();
//        model.addAttribute("locations", locations);
//        model.addAttribute("stop", stopDto);
//        return "edit-stop";
//    }

//    @PostMapping("/update")
//    public String updateStop(@ModelAttribute @Valid StopEditDto dto) {
//        Stop stop = stopMapper.fromDto(dto);
//        stopService.update(stop);
//        return "redirect:/stops/table";
//    }

//    @PostMapping("/deleteStop/{id}")
//    public String deleteStop(@PathVariable int id) {
//        stopService.delete(id);
//        return "redirect:/stops/table"; // Перенаправление обратно на страницу управления данными
//    }

//    @PostMapping("/add")
//    public String save(@ModelAttribute @Valid StopSaveDto dto) {
//        Stop stop = stopMapper.fromDto(dto);
//        Stop save = stopService.save(stop);
//        return "redirect:/stops/table"; // Перенаправление обратно на страницу управления данными
//    }
}