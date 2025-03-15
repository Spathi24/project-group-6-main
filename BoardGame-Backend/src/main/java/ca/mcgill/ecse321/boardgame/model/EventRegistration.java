package ca.mcgill.ecse321.boardgame.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "EventRegistration")
public class EventRegistration {

    @EmbeddedId
    private EventRegistrationKey eventRegistrationKey;

    @Enumerated(EnumType.STRING)
    private ParticipationStatus participationStatus;

    protected EventRegistration(){}

    public EventRegistration(EventRegistrationKey eventRegistrationKey,ParticipationStatus participationStatus){
        this.eventRegistrationKey = eventRegistrationKey;
        this.participationStatus = participationStatus;
    }

    public EventRegistrationKey getEventRegistrationKey(){
        return eventRegistrationKey;
    }

    public ParticipationStatus getParticipationStatus(){
        return participationStatus;
    }

    @Embeddable
    public static class EventRegistrationKey implements Serializable{

        @ManyToOne
        private UserAccount registrant;

        @ManyToOne
        private Event event;

        public EventRegistrationKey() {
        }

        public EventRegistrationKey(UserAccount registrant, Event event) {
            this.registrant = registrant;
            this.event = event;
        }

        public UserAccount getRegistrant() {
            return registrant;
        }

        public Event getEvent() {
            return event;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof EventRegistrationKey)) {
                return false;
            }
            EventRegistrationKey that = (EventRegistrationKey) obj;
            return Objects.equals(this.registrant.getUserAccountID(), that.registrant.getUserAccountID())
                    && Objects.equals(this.event.getEventID(), that.event.getEventID());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.registrant.getUserAccountID(), this.event.getEventID());
        }
    }
}
