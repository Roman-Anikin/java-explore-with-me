package explore.app.comments.admin;

import explore.app.comments.Comment;
import explore.app.comments.CommentMapper;
import explore.app.comments.CommentRepository;
import explore.app.comments.dto.CommentDto;
import explore.app.events.userprivate.PrivateEventService;
import explore.app.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentMapper commentMapper;
    private final PrivateEventService eventService;
    private final CommentRepository repository;

    @Override
    @Transactional
    public CommentDto update(Long eventId, CommentDto commentDto) {
        eventService.getEventById(eventId);
        Comment comment = checkComment(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setCreated(LocalDateTime.now());
        comment.setEdited(true);
        log.info("Администратор отредактировал комментарий {}", comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public void delete(Long eventId, Long commentId) {
        eventService.getEventById(eventId);
        Comment comment = checkComment(commentId);
        repository.delete(comment);
        log.info("Администратор удалил комментарий {}", comment);
    }

    private Comment checkComment(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Комментарий с id " + commentId + " не найден"));
    }
}
