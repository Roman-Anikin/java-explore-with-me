package ru.practicum.comments.userpublic;

import ru.practicum.comments.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> getAllByEvent(Long eventId, Integer from, Integer size, String sort);
}
