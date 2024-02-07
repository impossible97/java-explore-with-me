package ru.practicum.admin_api.events.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.events.dto.Location;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {

    long id;
    String title;
    String annotation;
    long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    int participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
}
