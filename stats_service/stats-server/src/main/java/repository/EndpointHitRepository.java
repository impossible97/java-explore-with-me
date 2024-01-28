package repository;

import model.EndpointHit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends PagingAndSortingRepository<EndpointHit, Long> {
    Long countDistinctByIpAndUri(String ip, String uri);

    Long countByIpAndUri(String ip, String uri);

    List<EndpointHit> findAllByTimestampBetweenAndUriInOrderByTimestamp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
