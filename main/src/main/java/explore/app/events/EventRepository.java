package explore.app.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    String GRAPH = "event-entity-graph";

    @EntityGraph(GRAPH)
    @NonNull
    Page<Event> findAll(@NonNull Specification<Event> specification, @NonNull Pageable pageable);

    @EntityGraph(GRAPH)
    @NonNull
    Optional<Event> findById(@NonNull Long id);

    @EntityGraph(GRAPH)
    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @EntityGraph(GRAPH)
    Event findByInitiatorIdAndId(Long initiatorId, Long eventId);

    @EntityGraph(GRAPH)
    Event findByIdAndStateLike(Long eventId, EventState state);

}
