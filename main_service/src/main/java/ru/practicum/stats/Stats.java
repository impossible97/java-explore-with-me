package ru.practicum.stats;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.BaseClient;
import ru.practicum.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class Stats {

    private final BaseClient baseClient;

    @Transactional
    public void hitStatistics(HttpServletRequest request) {
        baseClient.post("/hit", EndpointHitDto.builder()
                .app("main_service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());
    }
}
