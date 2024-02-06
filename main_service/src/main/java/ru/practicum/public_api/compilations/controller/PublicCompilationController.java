package ru.practicum.public_api.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.compilations.dto.CompilationDto;
import ru.practicum.public_api.compilations.service.PublicCompilationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("compilation")
public class PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned") boolean pinned,
                                                @RequestParam(name = "from") int from,
                                                @RequestParam(name = "size") int size) {
        return publicCompilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilationById(@PathVariable long compId) {
        return publicCompilationService.getCompilationById(compId);
    }
}
