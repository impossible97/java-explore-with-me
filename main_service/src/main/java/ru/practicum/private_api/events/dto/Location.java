package ru.practicum.private_api.events.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
@Builder
public class Location {

    float lat;
    float lon;
}

