package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.service.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Information is saved")
    public void saveStatistics(@RequestHeader(name = "X-Service-Name", required = false) String appName,
                               @RequestParam(name = "uri", required = false) String uri,
                               @RequestParam(name = "ip", required = false) String ip,
                               HttpServletRequest request) {
        statisticsService.saveStatistics(appName, uri, ip, request);
    }

    @GetMapping("/stats")
    public List<EndpointHitDto> getStatistics(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(value = "uris", required = false) List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statisticsService.getStats(start, end, uris, unique);
    }
}