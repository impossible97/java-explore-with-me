package ru.practicum.private_api.event.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.user.dto.UserShortDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShortEventDto {

    long id;
    String title;
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    String eventDate;
    UserShortDto initiator;
    boolean paid;
    int views;
}
