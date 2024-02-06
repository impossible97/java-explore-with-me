package ru.practicum.admin_api.compilations.service;

import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.dto.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    CompilationDto patchCompilation(long comId, NewCompilationDto newCompilationDto);
}
