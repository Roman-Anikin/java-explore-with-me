package ru.practicum.comments;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.events.Event;
import ru.practicum.users.User;
import ru.practicum.utils.CustomDateFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentMapper {

    private CustomDateFormatter formatter;

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getCommentator().getName(),
                comment.getText(),
                formatter.dateToString(comment.getCreated()),
                comment.isEdited());
    }

    public List<CommentDto> toDto(List<Comment> comments) {
        return comments
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Comment fromDto(CommentDto commentDto) {
        return new Comment(commentDto.getId(),
                new User(),
                new Event(),
                commentDto.getText(),
                formatter.stringToDate(commentDto.getCreated()),
                commentDto.isEdited());
    }
}
