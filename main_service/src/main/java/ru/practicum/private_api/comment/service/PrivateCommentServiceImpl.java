package ru.practicum.private_api.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.private_api.comment.model.Comment;
import ru.practicum.private_api.comment.repository.CommentRepository;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDto createComment(long userId, long eventId, CommentDto commentDto) {
        User user = validateUser(userId);
        Event event = validateEvent(eventId);

        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(commentDto, user, event)));
    }

    @Transactional
    @Override
    public CommentDto patchComment(long userId, long eventId, long commentId, CommentDto commentDto) {
        validateUser(userId);
        validateEvent(eventId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Комментария с таким id = " + commentId + " не найдено"));

        if (comment.getAuthor().getId() != userId) {
            throw new ValidationException("Пользватель с id = " + userId + " не является автором комментария");
        }
        comment.setText(commentDto.getText());

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteComment(long userId, long eventId, long commentId) {
        validateUser(userId);
        validateEvent(eventId);
        if (commentRepository.findById(commentId).orElseThrow().getAuthor().getId() != userId) {
            throw new ValidationException("Пользватель с id = " + userId + " не является автором комментария с id = " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    private Event validateEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Собития с таким id = " + eventId + " не найдено"));
    }

    private User validateUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getAllCommentsByUser(long userId) {
        validateUser(userId);
        return commentRepository.findAllByAuthorId(userId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getComment(long userId, long commentId) {
        validateUser(userId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Комментария с таким id = " + commentId + " не найдено"));
        return commentMapper.toDto(comment);
    }
}
