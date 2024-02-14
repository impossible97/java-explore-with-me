package ru.practicum.private_api.comment.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    List<Comment> findAllByAuthorId(long userId);

    Comment findByIdAndAuthorId(long commentId, long userId);

    List<Comment> findAllByEventId(long eventId);
}
