package ru.practicum.comments.userprivate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.Comment;
import ru.practicum.comments.CommentMapper;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.events.Event;
import ru.practicum.events.EventState;
import ru.practicum.events.userprivate.PrivateEventService;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.users.User;
import ru.practicum.users.UserService;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentMapper commentMapper;
    private final UserService userService;
    private final PrivateEventService eventService;
    private final CommentRepository repository;

    @Override
    public CommentDto add(Long userId, Long eventId, CommentDto commentDto) {
        User user = userService.getById(userId);
        Event event = eventService.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ValidationException("Событие ещё не опубликовано");
        }
        Comment comment = commentMapper.fromDto(commentDto);
        comment.setCommentator(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        repository.save(comment);
        log.info("Добавлен комментарий {}", comment);
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public CommentDto update(Long userId, Long eventId, CommentDto commentDto) {
        userService.getById(userId);
        eventService.getEventById(eventId);
        Comment comment = checkComment(commentDto.getId());
        checkOwner(comment, userId);
        comment.setText(commentDto.getText());
        comment.setCreated(LocalDateTime.now());
        comment.setEdited(true);
        log.info("Отредактирован комментарий {}", comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public void delete(Long userId, Long eventId, Long commentId) {
        userService.getById(userId);
        eventService.getEventById(eventId);
        Comment comment = checkComment(commentId);
        checkOwner(comment, userId);
        repository.delete(comment);
        log.info("Удален комментарий {}", comment);
    }

    private void checkOwner(Comment comment, Long userId) {
        if (!comment.getCommentator().getId().equals(userId)) {
            throw new ValidationException("Пользователь не является владельцем комментария");
        }
    }

    private Comment checkComment(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Комментарий с id " + commentId + " не найден"));
    }
}
