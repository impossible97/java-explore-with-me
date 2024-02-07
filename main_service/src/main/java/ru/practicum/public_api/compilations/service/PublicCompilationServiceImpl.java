package ru.practicum.public_api.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.model.Compilation;
import ru.practicum.admin_api.compilations.repository.CompilationRepository;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.private_api.events.dto.ShortEventDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService{

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        return compilationRepository.findAllByPinned(pinned, PageRequest.of(from / size, size)).stream()
                .filter(compilation -> compilation.getPinned() == pinned)
                .map(compilation -> compilationMapper.toDto(compilation, compilation.getEvents().stream()
                            .map(event ->
                                    eventMapper.toShortDto(event,
                                            categoryMapper.toDto(event.getCategory()),
                                            userRepository.findUserById(event.getInitiator().getId())))
                            .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("Подборка с таким id = " + compId + " не найдена"));
        List<ShortEventDto> shortEventDtos = compilation.getEvents().stream()
                .map(event ->
                        eventMapper.toShortDto(event,
                                categoryMapper.toDto(event.getCategory()),
                                userRepository.findUserById(event.getInitiator().getId())))
                .collect(Collectors.toList());
        return compilationMapper.toDto(compilation, shortEventDtos);
    }
}
