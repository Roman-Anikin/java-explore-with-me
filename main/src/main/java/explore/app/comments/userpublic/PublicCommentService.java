package explore.app.comments.userpublic;

import explore.app.comments.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> getAllByEvent(Long eventId, Integer from, Integer size, String sort);
}
