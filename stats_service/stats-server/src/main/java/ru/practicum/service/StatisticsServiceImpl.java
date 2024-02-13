package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final EndpointHitRepository repository;
    private final EndpointHitMapper mapper;

    @Transactional
    @Override
    public void hit(EndpointHitDto endpointHitDto) {
        repository.save(mapper.toEntity(endpointHitDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique, int from, int size) {

        if (start.isAfter(end)) {
            throw new ValidationException("Время начала не может быть позже времени окончания выборки");
        }

        if (uris != null) {
            if (unique) {
                return repository.findAllDistinctElementsWithUris(start, end, uris, PageRequest.of(from / size, size))
                        .stream()
                        .sorted(Comparator.comparingLong(ViewStats::getHits).reversed())
                        .distinct()
                        .collect(Collectors.toList());
            } else {
                return repository.findAllElementsWithUris(start, end, uris, PageRequest.of(from / size, size))
                        .stream()
                        .sorted(Comparator.comparingLong(ViewStats::getHits).reversed())
                        .distinct()
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return repository.findAllDistinctElementsWithoutUris(start, end, PageRequest.of(from / size, size))
                        .stream()
                        .sorted(Comparator.comparingLong(ViewStats::getHits).reversed())
                        .distinct()
                        .collect(Collectors.toList());
            } else {
                return repository.findAllElementsWithoutUris(start, end, PageRequest.of(from / size, size))
                        .stream()
                        .sorted(Comparator.comparingLong(ViewStats::getHits).reversed())
                        .distinct()
                        .collect(Collectors.toList());
            }
        }
    }
}
