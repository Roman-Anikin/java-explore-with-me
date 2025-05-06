package explore.app.comments.userprivate;

import explore.app.comments.dto.CommentDto;

public interface PrivateCommentService {

    CommentDto add(Long userId, Long eventId, CommentDto commentDto);

    CommentDto update(Long userId, Long eventId, CommentDto commentDto);

    void delete(Long userId, Long eventId, Long commentId);
}
