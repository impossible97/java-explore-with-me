package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final EndpointHitRepository repository;
    private final EndpointHitMapper mapper;

    @Transactional
    @Override
    public void hit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = mapper.toEntity(endpointHitDto);

        repository.save(endpointHit);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique, int from, int size) {

        if (uris != null) {
            if (unique) {
                return new HashSet<>(repository.findAllDistinctElementsWithUris(start, end, uris, PageRequest.of(from / size, size)));
            } else {
                return new HashSet<>(repository.findAllElementsWithUris(start, end, uris, PageRequest.of(from / size, size)));
            }
        } else {
            if (unique) {
                return new HashSet<>(repository.findAllDistinctElementsWithoutUris(start, end, PageRequest.of(from / size, size)));
            } else {
                return new HashSet<>(repository.findAllElementsWithoutUris(start, end, PageRequest.of(from / size, size)));
            }
        }
    }
}
