package ru.practicum.comments.userpublic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.comments.Comment;
import ru.practicum.comments.CommentMapper;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.CommentSort;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.events.userprivate.PrivateEventService;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.utils.OffsetPageRequest;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentMapper commentMapper;
    private final PrivateEventService eventService;
    private final CommentRepository repository;

    @Override
    public List<CommentDto> getAllByEvent(Long eventId, Integer from, Integer size, String sort) {
        checkSort(sort);
        eventService.getEventById(eventId);
        List<Comment> comments = repository.findAllByEventId(eventId, getPageable(from, size, sort));
        log.info("Получен список комментариев {}", comments);
        return commentMapper.toDto(comments);
    }

    private void checkSort(String sort) {
        if (!sort.equals(CommentSort.ASC.name()) && !sort.equals(CommentSort.DESC.name())) {
            throw new ValidationException("Неверный формат сортировки");
        }
    }

    private Pageable getPageable(Integer from, Integer size, String sort) {
        return new OffsetPageRequest(from, size, getSorting(sort));
    }

    private Sort getSorting(String sort) {
        return Sort.by(Sort.Direction.valueOf(sort), "created");
    }
}
