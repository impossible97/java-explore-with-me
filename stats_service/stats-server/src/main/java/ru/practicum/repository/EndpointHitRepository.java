package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends PagingAndSortingRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) FROM EndpointHit eh" +
            " WHERE eh.timestamp BETWEEN :start AND :end AND eh.uri IN :uris" +
            " group by eh.app, eh.uri" +
            " order by count(distinct eh.ip) desc")
    List<ViewStats> findAllDistinctElementsWithUris(@Param("start") LocalDateTime start,
                                                   @Param("end") LocalDateTime end,
                                                   @Param("uris") List<String> uris,
                                                   Pageable pageable);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) FROM EndpointHit eh" +
            " WHERE eh.timestamp BETWEEN :start AND :end " +
            " group by eh.app, eh.uri" +
            " order by count(distinct eh.ip) desc")
    List<ViewStats> findAllDistinctElementsWithoutUris(@Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end,
                                                    Pageable pageable);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) FROM EndpointHit eh" +
            " WHERE eh.timestamp BETWEEN :start AND :end AND eh.uri IN :uris" +
            " group by eh.app, eh.uri" +
            " order by count(distinct eh.ip) desc")
    List<ViewStats> findAllElementsWithUris(@Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end,
                                                    @Param("uris") List<String> uris,
                                                    Pageable pageable);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) FROM EndpointHit eh" +
            " WHERE eh.timestamp BETWEEN :start AND :end " +
            " group by eh.app, eh.uri" +
            " order by count(distinct eh.ip) desc")
    List<ViewStats> findAllElementsWithoutUris(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end,
                                            Pageable pageable);
}