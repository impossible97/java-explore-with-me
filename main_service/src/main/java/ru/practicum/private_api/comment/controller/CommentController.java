package ru.practicum.private_api.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.private_api.comment.service.CommentService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("users/{userId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable(name = "userId") long userId,
                                    @PathVariable(name = "eventId") long eventId,
                                    @Valid @RequestBody CommentDto newCommentDto) {
        return commentService.createComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("{eventId}/{commentId}")
    public CommentDto patchComment(@PathVariable(name = "userId") long userId,
                                   @PathVariable(name = "eventId") long eventId,
                                   @PathVariable(name = "commentId") long commentId,
                                   @RequestBody CommentDto newCommentDto) {
        return commentService.patchComment(userId, eventId, commentId, newCommentDto);
    }

    @DeleteMapping("{eventId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(name = "userId") long userId,
                              @PathVariable(name = "eventId") long eventId,
                              @PathVariable(name = "commentId") long commentId) {
        commentService.deleteComment(userId, eventId, commentId);
    }
}
