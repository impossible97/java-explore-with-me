package service;

import ru.practicum.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsService {

    List<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    void saveStatistics(HttpServletRequest request);
}
