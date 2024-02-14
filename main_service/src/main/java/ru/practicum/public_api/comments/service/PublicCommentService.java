package ru.practicum.public_api.comments.service;

import ru.practicum.private_api.comment.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {
    List<CommentDto> getAllCommentsByEvent(long eventId);
}
