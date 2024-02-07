package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.users.dto.UserShortDto;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.Location;
import ru.practicum.private_api.events.dto.NewEventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.events.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class EventMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event toEntity(NewEventDto newEventDto, User user, Category category) {
        Event event = new Event();

        event.setTitle(newEventDto.getTitle());
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setCreatedOn(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter));
        event.setInitiator(user);
        event.setLocation(Map.of(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()));
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());

        return event;
    }

    public EventDto toDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto) {
        EventDto eventDto = new EventDto();

        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategory(categoryDto);
        eventDto.setConfirmedRequests(event.getConfirmedRequests());
        eventDto.setCreatedOn(event.getCreatedOn().format(formatter));
        eventDto.setDescription(event.getDescription());
        eventDto.setEventDate(event.getEventDate().format(formatter));
        eventDto.setInitiator(userShortDto);
        eventDto.setLocation(mapLocation(event.getLocation()));
        eventDto.setPaid(event.getPaid());
        eventDto.setParticipantLimit(event.getParticipantLimit());
        eventDto.setRequestModeration(event.getRequestModeration());
        eventDto.setState(event.getState());
        eventDto.setViews(event.getViews());
        if (event.getPublishedOn() != null) {
            eventDto.setPublishedOn(event.getPublishedOn().format(formatter));
        }

        return eventDto;
    }

    public Location mapLocation(Map<Float, Float> location) {
        if (location == null || location.isEmpty()) {
            return null;
        }
        Map.Entry<Float, Float> entry = location.entrySet().iterator().next();

        return Location.builder().lat(entry.getKey()).lon(entry.getValue()).build();
    }

    public ShortEventDto toShortDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto) {
        ShortEventDto shortEventDto = new ShortEventDto();

        shortEventDto.setId(event.getId());
        shortEventDto.setTitle(event.getTitle());
        shortEventDto.setAnnotation(event.getAnnotation());
        shortEventDto.setCategory(categoryDto);
        shortEventDto.setConfirmedRequests(event.getConfirmedRequests());
        shortEventDto.setEventDate(event.getEventDate().format(formatter));
        shortEventDto.setInitiator(userShortDto);
        shortEventDto.setPaid(event.getPaid());
        shortEventDto.setViews(event.getViews());

        return shortEventDto;
    }
}
