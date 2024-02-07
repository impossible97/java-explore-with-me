package ru.practicum.private_api.events.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.users.dto.UserShortDto;
import ru.practicum.private_api.events.model.EventState;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {

    long id;
    String title;
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    int participantLimit;
    String publishedOn;
    Boolean requestModeration;
    EventState state;
    int views;
}
