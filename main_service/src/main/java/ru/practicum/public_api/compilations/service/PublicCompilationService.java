package ru.practicum.public_api.compilations.service;

import ru.practicum.admin_api.compilations.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilationById(long compId);
}
