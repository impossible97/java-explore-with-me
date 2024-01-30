package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.service.StatisticsService;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStatistics(@RequestHeader(name = "X-Service-Name", required = false) String appName,
                               @RequestParam(name = "uri", required = false) String uri,
                               @RequestParam(name = "ip", required = false) String ip) {
        statisticsService.saveStatistics(appName, uri, ip);
    }

    @GetMapping("/stats")
    public List<EndpointHitDto> getStatistics(@RequestParam(name = "start") String start,
                                              @RequestParam(name = "end") String end,
                                              @RequestParam(value = "uris", required = false) List<String> uris,
                                              @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statisticsService.getStats(start, end, uris, unique);
    }
}
