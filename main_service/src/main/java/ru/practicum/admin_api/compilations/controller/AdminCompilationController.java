package ru.practicum.admin_api.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.dto.NewCompilationDto;
import ru.practicum.admin_api.compilations.dto.UpdatedCompilationDto;
import ru.practicum.admin_api.compilations.service.AdminCompilationService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("admin/compilations")
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return adminCompilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("{compId}")
    public CompilationDto patchCompilation(@PathVariable long compId, @Valid @RequestBody UpdatedCompilationDto newCompilationDto) {
        return adminCompilationService.patchCompilation(compId, newCompilationDto);
    }
}
