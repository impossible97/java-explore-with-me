package ru.practicum.private_api.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.user.dto.UserShortDto;
import ru.practicum.private_api.event.model.EventState;

import java.time.LocalDateTime;

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
