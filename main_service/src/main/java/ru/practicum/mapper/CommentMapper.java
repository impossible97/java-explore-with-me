package ru.practicum.mapper;

import org.springframework.stereotype.Service;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.private_api.comment.model.Comment;
import ru.practicum.private_api.events.model.Event;

import java.time.LocalDateTime;

@Service
public class CommentMapper {

    public Comment toEntity(CommentDto commentDto, User user, Event event) {
        Comment comment = new Comment();

        comment.setText(commentDto.getText());
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());

        return comment;
    }

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setAuthor(comment.getAuthor().getName());
        commentDto.setCreated(comment.getCreated());

        return commentDto;
    }
}
