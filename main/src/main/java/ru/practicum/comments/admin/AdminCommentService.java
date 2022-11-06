package ru.practicum.comments.admin;

import ru.practicum.comments.dto.CommentDto;

public interface AdminCommentService {

    CommentDto update(Long eventId, CommentDto commentDto);

    void delete(Long eventId, Long commentId);

}
