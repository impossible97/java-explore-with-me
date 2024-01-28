package service;

import ru.practicum.dto.EndpointHitDto;
import lombok.AllArgsConstructor;
import mapper.EndpointHitMapper;
import model.EndpointHit;
import org.springframework.stereotype.Service;
import repository.EndpointHitRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final EndpointHitRepository repository;
    private final EndpointHitMapper mapper;

    @Override
    public void saveStatistics(HttpServletRequest request) {
        EndpointHit endpointHit = mapper.toEntity(makeDtoFromRequest(request));

        repository.save(endpointHit);
    }

    @Override
    public List<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (unique) {
            return repository.findAllByTimestampBetweenAndUriInOrderByTimestamp(start, end, uris).stream()
                    .map(endpointHit -> mapper.toDto(endpointHit,
                                                     repository.countDistinctByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                    .collect(Collectors.toList());
        } else {
            return repository.findAllByTimestampBetweenAndUriInOrderByTimestamp(start, end, uris).stream()
                    .map(endpointHit -> mapper.toDto(endpointHit,
                                                     repository.countByIpAndUri(endpointHit.getIp(), endpointHit.getUri())))
                    .collect(Collectors.toList());
        }
    }

    private EndpointHitDto makeDtoFromRequest(HttpServletRequest request) {
        String app = request.getHeader("X-Application-Name");
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();

        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setApp(app);
        endpointHitDto.setUri(uri);
        endpointHitDto.setIp(ip);

        return endpointHitDto;
    }
}
