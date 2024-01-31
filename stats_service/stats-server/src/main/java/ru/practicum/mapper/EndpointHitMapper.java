package ru.practicum.mapper;

import ru.practicum.dto.EndpointHitDto;
import lombok.AllArgsConstructor;
import ru.practicum.model.EndpointHit;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class EndpointHitMapper {

    public EndpointHit toEntity(EndpointHitDto endpointHitDto) {

        EndpointHit endpointHit = new EndpointHit();

        endpointHit.setApp(endpointHitDto.getApp());
        endpointHit.setIp(endpointHitDto.getIp());
        endpointHit.setUri(endpointHitDto.getUri());
        endpointHit.setTimestamp(LocalDateTime.now());

        return endpointHit;
    }

    public EndpointHitDto toDto(EndpointHit endpointHit, long hits) {

        EndpointHitDto endpointHitDto = new EndpointHitDto();

        endpointHitDto.setApp(endpointHit.getApp());
        endpointHitDto.setUri(endpointHit.getUri());
        endpointHitDto.setHits(hits);

        return endpointHitDto;
    }
}
