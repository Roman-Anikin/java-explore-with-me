package explore.app.comments.userpublic;

import explore.app.comments.dto.CommentDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/events/{eventId}/comments")
@Validated
public class PublicCommentController {

    private final PublicCommentService service;

    @GetMapping
    public List<CommentDto> getAllByEvent(@PathVariable Long eventId,
                                          @PositiveOrZero(message = "Значение from должно быть больше или равно нулю")
                                          @RequestParam(value = "from", defaultValue = "0") Integer from,
                                          @Positive(message = "Значение size должно быть больше нуля")
                                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                                          @RequestParam(value = "sort", defaultValue = "DESC") String sort) {
        return service.getAllByEvent(eventId, from, size, sort);
    }
}
