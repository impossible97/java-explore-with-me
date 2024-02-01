package ru.practicum.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class EndpointHitMapper {

    public EndpointHit toEntity(EndpointHitDto endpointHitDto) {

        EndpointHit endpointHit = new EndpointHit();

        endpointHit.setApp(endpointHitDto.getApp());
        endpointHit.setUri(endpointHitDto.getUri());
        endpointHit.setIp(endpointHitDto.getIp());
        endpointHit.setTimestamp(LocalDateTime.now());

        return endpointHit;
    }

    public ViewStats toDto(EndpointHit endpointHit, long hits) {

        ViewStats viewStats = new ViewStats();

        viewStats.setApp(endpointHit.getApp());
        viewStats.setUri(endpointHit.getUri());
        viewStats.setHits(hits);

        return viewStats;
    }
}
