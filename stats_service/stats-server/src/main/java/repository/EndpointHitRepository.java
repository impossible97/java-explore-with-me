package repository;

import model.EndpointHit;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EndpointHitRepository extends PagingAndSortingRepository<EndpointHit, Long> {
    Long countDistinctByIpOrderByTimestamp(String ip);
}
