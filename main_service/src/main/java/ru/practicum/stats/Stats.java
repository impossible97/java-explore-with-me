package ru.practicum.stats;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.client.BaseClient;
import ru.practicum.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class Stats {

    private final BaseClient baseClient;

    public void hitStatistics(HttpServletRequest request) {
        baseClient.post("/hit", EndpointHitDto.builder()
                .app("main_service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());
    }
}
