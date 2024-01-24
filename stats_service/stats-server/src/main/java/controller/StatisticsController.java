package controller;

import dto.EndpointHitDto;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import service.StatisticsService;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    public void saveStatistics() {
        statisticsService.saveStatistics();
    }

    @GetMapping("/stats")
    public List<EndpointHitDto> getStatistics() {

    }
}
