package ru.practicum.private_api.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.private_api.comment.model.Comment;
import ru.practicum.private_api.comment.repository.CommentRepository;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.repository.EventRepository;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto createComment(long userId, long eventId, CommentDto commentDto) {
        User user = validateUser(userId);
        Event event = validateEvent(eventId);

        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(commentDto, user, event)));
    }

    @Override
    public CommentDto patchComment(long userId, long eventId, long commentId, CommentDto commentDto) {
        validateUser(userId);
        validateEvent(eventId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Комментария с таким id = " + commentId + " не найдено"));

        comment.setText(commentDto.getText());

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long userId, long eventId, long commentId) {
        validateUser(userId);
        validateEvent(eventId);
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
}
