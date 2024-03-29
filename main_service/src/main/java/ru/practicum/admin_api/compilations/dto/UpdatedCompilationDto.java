package ru.practicum.admin_api.compilations.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatedCompilationDto {

    Set<Long> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}
