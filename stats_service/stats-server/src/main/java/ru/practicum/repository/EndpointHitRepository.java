package ru.practicum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends PagingAndSortingRepository<EndpointHit, Long> {

    @Query("SELECT COUNT(DISTINCT e.ip) FROM EndpointHit e WHERE e.uri = :uri")
    Long countDistinctByIp(String uri);

    Long countByIpAndUri(String ip, String uri);

    List<EndpointHit> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    List<EndpointHit> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);
}