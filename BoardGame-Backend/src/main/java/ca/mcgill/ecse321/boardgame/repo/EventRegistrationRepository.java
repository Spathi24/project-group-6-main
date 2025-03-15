package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.EventRegistration;
import org.springframework.data.repository.CrudRepository;

public interface EventRegistrationRepository extends CrudRepository<EventRegistration,EventRegistration.EventRegistrationKey> {
    public EventRegistration findByEventRegistrationKey(EventRegistration.EventRegistrationKey eventRegistrationKey);
}
