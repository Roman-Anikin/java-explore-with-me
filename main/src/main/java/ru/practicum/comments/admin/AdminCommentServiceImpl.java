package ru.practicum.comments.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.Comment;
import ru.practicum.comments.CommentMapper;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.events.userprivate.PrivateEventService;
import ru.practicum.exceptions.ObjectNotFoundException;

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
