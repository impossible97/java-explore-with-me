package ru.practicum.admin_api.compilations.service;

import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.dto.NewCompilationDto;
import ru.practicum.admin_api.compilations.dto.UpdatedCompilationDto;

public interface AdminCompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    CompilationDto patchCompilation(long comId, UpdatedCompilationDto newCompilationDto);
}
