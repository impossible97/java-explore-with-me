package ru.practicum.private_api.comment.service;

import ru.practicum.private_api.comment.dto.CommentDto;

import java.util.List;

public interface PrivateCommentService {
    CommentDto createComment(long userId, long eventId, CommentDto commentDto);

    CommentDto patchComment(long userId, long eventId, long commentId, CommentDto newCommentDto);

    void deleteComment(long userId, long eventId, long commentId);

    List<CommentDto> getAllCommentsByUser(long userId);

    CommentDto getComment(long userId, long commentId);
}
