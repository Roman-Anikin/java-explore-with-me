package ru.practicum.comments.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin/events/{eventId}/comments")
public class AdminCommentController {

    private final AdminCommentService service;

    @PatchMapping
    public CommentDto update(@PathVariable Long eventId,
                             @RequestBody CommentDto commentDto) {
        return service.update(eventId, commentDto);
    }

    @DeleteMapping(path = "/{commentId}")
    public void delete(@PathVariable Long eventId,
                       @PathVariable Long commentId) {
        service.delete(eventId, commentId);
    }
}
