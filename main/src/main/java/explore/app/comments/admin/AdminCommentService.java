package explore.app.comments.admin;

import explore.app.comments.dto.CommentDto;

public interface AdminCommentService {

    CommentDto update(Long eventId, CommentDto commentDto);

    void delete(Long eventId, Long commentId);

}
