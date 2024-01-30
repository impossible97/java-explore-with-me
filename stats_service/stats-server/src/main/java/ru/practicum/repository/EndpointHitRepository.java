package ru.practicum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.EndpointHit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends PagingAndSortingRepository<EndpointHit, Long> {
    @Query(value = "SELECT COUNT(DISTINCT e) FROM EndpointHit e WHERE e.ip = :ip AND e.uri = :uri")
    Long countDistinctByIpAndUri(@Param("ip") String ip, @Param("uri") String uri);

    Long countByIpAndUri(String ip, String uri);

    List<EndpointHit> findAllByTimestampBetweenAndUriInOrderByTimestamp(LocalDateTime start, LocalDateTime end, List<String> uris);

    List<EndpointHit> findAllByTimestampBetweenOrderByTimestamp(LocalDateTime start, LocalDateTime end);
}
