package ru.practicum.private_api.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.private_api.comment.service.PrivateCommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users/{userId}/comments")
public class PrivateCommentController {

    private final PrivateCommentService privateCommentService;

    @PostMapping("{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable(name = "userId") long userId,
                                    @PathVariable(name = "eventId") long eventId,
                                    @Valid @RequestBody CommentDto commentDto) {
        return privateCommentService.createComment(userId, eventId, commentDto);
    }

    @PatchMapping("{eventId}/{commentId}")
    public CommentDto patchComment(@PathVariable(name = "userId") long userId,
                                   @PathVariable(name = "eventId") long eventId,
                                   @PathVariable(name = "commentId") long commentId,
                                   @RequestBody CommentDto newCommentDto) {
        return privateCommentService.patchComment(userId, eventId, commentId, newCommentDto);
    }

    @DeleteMapping("{eventId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(name = "userId") long userId,
                              @PathVariable(name = "eventId") long eventId,
                              @PathVariable(name = "commentId") long commentId) {
        privateCommentService.deleteComment(userId, eventId, commentId);
    }

    @GetMapping
    public List<CommentDto> getAllCommentsByUser(@PathVariable(name = "userId") long userId) {
        return privateCommentService.getAllCommentsByUser(userId);
    }

    @GetMapping("{commentId}")
    public CommentDto getCommentByUser(@PathVariable(name = "userId") long userId,
                                       @PathVariable(name = "commentId") long commentId) {
        return privateCommentService.getComment(userId, commentId);
    }
}
