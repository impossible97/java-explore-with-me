package ru.practicum.public_api.comments.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.private_api.comment.dto.CommentDto;
import ru.practicum.public_api.comments.service.PublicCommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("comments")
public class PublicCommentController {

    private final PublicCommentService publicCommentService;


    @GetMapping("{eventId}")
    public List<CommentDto> getAllCommentsByEvent(@PathVariable(name = "eventId") long eventId) {
        return publicCommentService.getAllCommentsByEvent(eventId);
    }
}
