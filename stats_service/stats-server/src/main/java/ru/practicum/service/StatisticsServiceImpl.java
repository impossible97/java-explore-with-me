package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final EndpointHitRepository repository;
    private final EndpointHitMapper mapper;

    @Override
    public void saveStatistics(String app, String uri, String ip) {

        EndpointHit endpointHit = mapper.toEntity(makeDtoFromRequest(app, uri), ip);

        repository.save(endpointHit);
    }

    @Override
    public List<EndpointHitDto> getStats(String requestStart, String requestEnd, List<String> uris, Boolean unique) {
        LocalDateTime start = LocalDateTime.parse(requestStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(requestEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (uris != null) {
            if (unique) {
                return repository.findAllByTimestampBetweenAndUriInOrderByTimestamp(start, end, uris).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countDistinctByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                        .collect(Collectors.toList());
            } else {
                return repository.findAllByTimestampBetweenAndUriInOrderByTimestamp(start, end, uris).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return repository.findAllByTimestampBetweenOrderByTimestamp(start, end).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countDistinctByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                        .collect(Collectors.toList());
            } else {
                return repository.findAllByTimestampBetweenOrderByTimestamp(start, end).stream()
                        .map(endpointHit ->
                                mapper.toDto(endpointHit,
                                        repository.countByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                        .collect(Collectors.toList());
            }
        }
    }

    private EndpointHitDto makeDtoFromRequest(String app, String uri) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();

        endpointHitDto.setApp(app);
        endpointHitDto.setUri(uri);

        return endpointHitDto;
    }
}
