package ru.practicum.admin_api.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.admin_api.compilations.model.Compilation;

import java.util.List;

public interface CompilationRepository extends PagingAndSortingRepository<Compilation, Long> {

    List<Compilation> findAllByPinned(boolean pinned, Pageable pageable);
}
