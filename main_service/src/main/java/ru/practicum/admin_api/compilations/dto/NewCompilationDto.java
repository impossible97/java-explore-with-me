package ru.practicum.admin_api.compilations.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {

    long id;
    List<Long> events;
    boolean pinned;
    String title;
}
