package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final EndpointHitRepository repository;
    private final EndpointHitMapper mapper;

    @Override
    public void saveStatistics(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = mapper.toEntity(endpointHitDto);

        repository.save(endpointHit);
    }

    @Override
    public Set<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique, int from, int size) {

        if (uris != null) {
            if (unique) {
                return repository.findAllByTimestampBetweenAndUriIn(start, end, uris).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countDistinctByIp(endpointHit.getUri())))
                        .sorted(Comparator.comparing(EndpointHitDto::getHits).reversed())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            } else {
                return repository.findAllByTimestampBetweenAndUriIn(start, end, uris).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                        .sorted(Comparator.comparing(EndpointHitDto::getHits).reversed())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            }
        } else {
            if (unique) {
                return repository.findAllByTimestampBetween(start, end).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countDistinctByIp(endpointHit.getUri())))
                        .sorted(Comparator.comparing(EndpointHitDto::getHits).reversed())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            } else {
                return repository.findAllByTimestampBetween(start, end).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                        .sorted(Comparator.comparing(EndpointHitDto::getHits).reversed())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            }
        }
    }
}
