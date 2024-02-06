package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.dto.NewCompilationDto;
import ru.practicum.admin_api.compilations.model.Compilation;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.events.model.Event;

import java.util.List;

@Component
public class CompilationMapper {

    public Compilation toEntity(NewCompilationDto newCompilationDto, List<Event> events) {
        Compilation compilation = new Compilation();

        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.isPinned());
        compilation.setEvents(events);

        return compilation;
    }

    public CompilationDto toDto(Compilation compilation, List<ShortEventDto> shortEventDtos) {
        CompilationDto compilationDto = new CompilationDto();

        compilationDto.setId(compilation.getId());
        compilationDto.setEvents(shortEventDtos);
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setPinned(compilation.isPinned());

        return compilationDto;
    }
}
