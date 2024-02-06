package ru.practicum.private_api.events.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.users.dto.UserShortDto;
import ru.practicum.private_api.events.model.EventState;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    boolean paid;
    int participantLimit;
    String publishedOn;
    boolean requestModeration;
    EventState state;
    int views;
}
