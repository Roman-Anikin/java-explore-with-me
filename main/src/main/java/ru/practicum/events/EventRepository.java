package ru.practicum.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    String GRAPH = "event-entity-graph";

    @EntityGraph(GRAPH)
    Page<Event> findAll(@Nullable Specification<Event> specification, @Nullable Pageable pageable);

    @EntityGraph(GRAPH)
    Optional<Event> findById(@Nullable Long id);

    @EntityGraph(GRAPH)
    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @EntityGraph(GRAPH)
    Event findByInitiatorIdAndId(Long initiatorId, Long eventId);

    @EntityGraph(GRAPH)
    Event findByIdAndStateLike(Long eventId, EventState state);

}
