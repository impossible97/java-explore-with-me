package ru.practicum.admin_api.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.admin_api.compilations.dto.NewCompilationDto;
import ru.practicum.admin_api.compilations.service.AdminCompilationService;

@RestController
@AllArgsConstructor
@RequestMapping("admin/compilation")
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return adminCompilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("{comId}")
    public CompilationDto patchCompilation(@PathVariable long comId, @RequestBody NewCompilationDto newCompilationDto) {
        return adminCompilationService.patchCompilation(comId, newCompilationDto);
    }
}
