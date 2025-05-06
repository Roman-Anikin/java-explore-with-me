package explore.app.comments.userprivate;

import explore.app.comments.dto.CommentDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final PrivateCommentService service;

    @PostMapping
    public CommentDto add(@PathVariable Long userId, @PathVariable Long eventId,
                          @Valid @RequestBody CommentDto commentDto) {
        return service.add(userId, eventId, commentDto);
    }

    @PatchMapping
    public CommentDto update(@PathVariable Long userId, @PathVariable Long eventId,
                             @Valid @RequestBody CommentDto commentDto) {
        return service.update(userId, eventId, commentDto);
    }

    @DeleteMapping(path = "/{commentId}")
    public void delete(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long commentId) {
        service.delete(userId, eventId, commentId);
    }
}
