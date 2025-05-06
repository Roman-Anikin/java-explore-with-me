package explore.app.events.dto;

import explore.app.categories.dto.CategoryDto;
import explore.app.comments.dto.CommentDto;
import explore.app.users.dto.ShortUserDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShortEventDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String eventDate;
    private ShortUserDto initiator;
    private Boolean paid;
    private String title;
    private int views;
    private List<CommentDto> comments;
}
