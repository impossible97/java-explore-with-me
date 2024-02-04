package ru.practicum.private_api.event.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.user.dto.UserShortDto;
import ru.practicum.private_api.event.model.EventState;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {

    long id;
    String title;
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    UserShortDto initiator;
    Map<Float, Float> location;
    boolean paid;
    int participantLimit;
    LocalDateTime publishedOn;
    boolean requestModeration;
    EventState eventState;
    int views;
}
