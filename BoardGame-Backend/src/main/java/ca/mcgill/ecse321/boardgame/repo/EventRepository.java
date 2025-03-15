package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,Long> {
    public Event findEventByEventID(long eventID);
}
