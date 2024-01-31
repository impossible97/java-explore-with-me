package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StatisticsService {

    Set<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique, int from, int size);

    void saveStatistics(EndpointHitDto endpointHitDto);
}
