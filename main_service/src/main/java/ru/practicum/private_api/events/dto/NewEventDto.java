package ru.practicum.private_api.events.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    long id;
    String title;
    String annotation;
    long category;
    String description;
    String eventDate;
    Location location;
    boolean paid;
    int participantLimit;
    boolean requestModeration;
}
