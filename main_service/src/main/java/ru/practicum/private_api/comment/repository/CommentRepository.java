package ru.practicum.private_api.comment.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.comment.model.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
