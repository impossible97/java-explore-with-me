package controller;

import ru.practicum.dto.EndpointHitDto;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    public void saveStatistics(HttpServletRequest request) {
        statisticsService.saveStatistics(request);
    }

    @GetMapping("/stats")
    public List<EndpointHitDto> getStatistics(@RequestParam(name = "start") LocalDateTime start,
                                              @RequestParam(name = "end") LocalDateTime end,
                                              @RequestParam("uris") List<String> uris,
                                              @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statisticsService.getStats(start, end, uris, unique);

    }
}
