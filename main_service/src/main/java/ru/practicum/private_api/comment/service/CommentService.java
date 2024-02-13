package ru.practicum.private_api.comment.service;

import ru.practicum.private_api.comment.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(long userId, long eventId, CommentDto newCommentDto);

    CommentDto patchComment(long userId, long eventId, long commentId, CommentDto newCommentDto);

    void deleteComment(long userId, long eventId, long commentId);
}
