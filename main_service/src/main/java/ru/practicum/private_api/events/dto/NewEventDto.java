package ru.practicum.private_api.events.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    @NotBlank
    @Size(min = 3, max = 120)
    String title;
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    @NotNull
    String eventDate;
    @NotNull
    Location location;
    Boolean paid;
    @PositiveOrZero
    int participantLimit;
    Boolean requestModeration;
}
