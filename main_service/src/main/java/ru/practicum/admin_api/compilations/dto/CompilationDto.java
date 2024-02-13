package ru.practicum.admin_api.compilations.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.events.dto.ShortEventDto;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {

    long id;
    List<ShortEventDto> events;
    Boolean pinned;
    String title;
}
