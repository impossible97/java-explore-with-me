package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import javax.servlet.http.HttpServletRequest;
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
    public void saveStatistics(String app, String uri, String ip, HttpServletRequest request) {

        EndpointHit endpointHit = mapper.toEntity(makeDtoFromRequest(app, uri), ip);

        String anotherIp = request.getRemoteAddr();
        String requestURI = request.getRequestURI();

        repository.save(endpointHit);
    }

    @Override
    public List<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

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
