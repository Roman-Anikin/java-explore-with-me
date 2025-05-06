package explore.app.comments;

import explore.app.events.Event;
import explore.app.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
