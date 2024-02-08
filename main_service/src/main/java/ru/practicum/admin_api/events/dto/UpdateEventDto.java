package ru.practicum.admin_api.events.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.events.dto.Location;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {

    @Size(min = 3, max = 120)
    String title;
    @Size(min = 20, max = 2000)
    String annotation;
    long category;
    @Size(min = 20, max = 7000)
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    @PositiveOrZero
    int participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
}
