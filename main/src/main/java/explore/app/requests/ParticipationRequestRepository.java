package explore.app.requests;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    String GRAPH = "request-entity-graph";

    @EntityGraph(GRAPH)
    List<ParticipationRequest> findAllByRequesterId(Long requesterId);

    @EntityGraph(GRAPH)
    List<ParticipationRequest> findAllByEventId(Long eventId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE requests SET status = 'REJECTED' WHERE event_id = ?1", nativeQuery = true)
    void rejectAll(Long eventId);

}
