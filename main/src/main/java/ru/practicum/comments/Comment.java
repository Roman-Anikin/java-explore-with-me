package ru.practicum.comments;

import lombok.*;
import ru.practicum.events.Event;
import ru.practicum.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "commentator_id")
    private User commentator;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")
    private Event event;

    private String text;
    private LocalDateTime created;

    @Column(name = "is_edited")
    private boolean edited;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentator=" + commentator +
                ", eventId=" + event.getId() +
                ", text='" + text + '\'' +
                ", created=" + created +
                '}';
    }
}
