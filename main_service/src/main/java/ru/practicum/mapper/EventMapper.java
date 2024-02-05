package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.user.dto.UserShortDto;
import ru.practicum.admin_api.user.model.User;
import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.event.dto.NewEventDto;
import ru.practicum.private_api.event.dto.ShortEventDto;
import ru.practicum.private_api.event.model.Event;
import ru.practicum.private_api.event.dto.Location;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class EventMapper {

    public Event toEntity(NewEventDto newEventDto, User user, Category category) {
        Event event = new Event();

        event.setTitle(newEventDto.getTitle());
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setCreatedOn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setEventDate(newEventDto.getEventDate());
        event.setInitiator(user);
        event.setLocation(Map.of(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()));
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());

        return event;
    }

    public EventDto toDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto) {
        EventDto eventDto = new EventDto();

        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategory(categoryDto);
        eventDto.setConfirmedRequests(event.getConfirmedRequests());
        eventDto.setCreatedOn(event.getCreatedOn());
        eventDto.setDescription(event.getDescription());
        eventDto.setEventDate(event.getEventDate());
        eventDto.setInitiator(userShortDto);
        eventDto.setLocation(mapLocation(event.getLocation()));
        eventDto.setPaid(event.isPaid());
        eventDto.setParticipantLimit(event.getParticipantLimit());
        eventDto.setRequestModeration(event.isRequestModeration());
        eventDto.setState(event.getState());
        eventDto.setViews(event.getViews());

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
        shortEventDto.setEventDate(event.getEventDate());
        shortEventDto.setInitiator(userShortDto);
        shortEventDto.setPaid(event.isPaid());
        shortEventDto.setViews(event.getViews());

        return shortEventDto;
    }
}
