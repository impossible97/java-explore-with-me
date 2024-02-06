package ru.practicum.admin_api.events.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;
import ru.practicum.private_api.events.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;


    @Override
    public List<EventDto> searchEvents(
            List<Long> users, List<EventState> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size) {

        List<Event> events = eventRepository.findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                users, states, categories, rangeStart, rangeEnd, PageRequest.of(from / size, size)
        );

        return events.stream().map(event ->
                        eventMapper.toDto(event,
                                          categoryMapper.toDto(event.getCategory()),
                                          userRepository.findUserById(event.getInitiator().getId())))
                .collect(Collectors.toList());
    } // Это сделано !
}
