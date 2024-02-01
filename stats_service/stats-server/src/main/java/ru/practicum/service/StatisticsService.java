package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StatisticsService {

    Set<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique, int from, int size);

    void hit(EndpointHitDto endpointHitDto);
}
