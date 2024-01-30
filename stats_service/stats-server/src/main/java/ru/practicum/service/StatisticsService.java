package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;

import java.util.List;

public interface StatisticsService {

    List<EndpointHitDto> getStats(String start, String end, List<String> uris, Boolean unique);

    void saveStatistics(String app, String uri, String ip);
}
