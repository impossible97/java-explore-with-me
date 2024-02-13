package ru.practicum.public_api.comments.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.private_api.comment.repository.CommentRepository;
import ru.practicum.private_api.events.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getAllCommentsByEvent(long eventId) {
        validateEvent(eventId);

        return commentRepository.findAllByEventId(eventId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateEvent(long eventId) {
        eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Собития с таким id = " + eventId + " не найдено"));
    }
}
