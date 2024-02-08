package ru.practicum.admin_api.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.dto.NewCompilationDto;
import ru.practicum.admin_api.compilations.dto.UpdatedCompilationDto;
import ru.practicum.admin_api.compilations.model.Compilation;
import ru.practicum.admin_api.compilations.repository.CompilationRepository;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.repository.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;
    private final CategoryMapper categoryMapper;

    @Transactional
    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getEvents() == null) {
            newCompilationDto.setEvents(new HashSet<>());
        }
        List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        List<ShortEventDto> shortEventDtos = events.stream()
                .map(event ->
                        eventMapper.toShortDto(event,
                                categoryMapper.toDto(event.getCategory()),
                                userRepository.findUserById(event.getInitiator().getId())))
                .collect(Collectors.toList());

        if (newCompilationDto.getPinned() == null) {
            newCompilationDto.setPinned(false);
        }
        return compilationMapper.toDto(
                compilationRepository.save(compilationMapper.toEntity(newCompilationDto, events)), shortEventDtos);
    }

    @Transactional
    @Override
    public void deleteCompilation(long compId) {
        compilationRepository.delete(compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("Подборки событий с таким id = " + compId + " не существует")));
    }

    @Transactional
    @Override
    public CompilationDto patchCompilation(long compId, UpdatedCompilationDto updatedCompilationDto) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("Подборки событий с таким id = " + compId + " не существует"));

        List<Event> events;

        if (updatedCompilationDto.getEvents() != null) {
            events = eventRepository.findAllByIdIn(updatedCompilationDto.getEvents());
            compilation.setEvents(events);
        } else {
            events = compilation.getEvents();
        }
        if (updatedCompilationDto.getTitle() != null) {
            compilation.setTitle(updatedCompilationDto.getTitle());
        }
        if (updatedCompilationDto.getPinned() != null) {
            compilation.setPinned(updatedCompilationDto.getPinned());
        }

        Compilation savedCompilation = compilationRepository.save(compilation);

        List<ShortEventDto> shortEventDtos = events.stream()
                .map(event ->
                        eventMapper.toShortDto(event,
                                categoryMapper.toDto(event.getCategory()),
                                userRepository.findUserById(event.getInitiator().getId())))
                .collect(Collectors.toList());

        return compilationMapper.toDto(savedCompilation, shortEventDtos);
    }
}
