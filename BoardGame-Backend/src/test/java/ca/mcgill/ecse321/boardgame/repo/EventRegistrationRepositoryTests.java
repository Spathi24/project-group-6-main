package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EventRegistrationRepositoryTests {

    @Autowired
    private EventRegistrationRepository eventRegistrationRepo;

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private UserAccountRepository creatorRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private UserAccountRepository registrantRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase(){
        eventRegistrationRepo.deleteAll();
        eventRepo.deleteAll();
        creatorRepo.deleteAll();;
        gameRepo.deleteAll();;;
        registrantRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadEventRegistration(){

        String name = "mudgamepad";
        String password = "1234567";
        String email = "sibo.jiang@mail.mcgill.ca";
        AccountType accountType = AccountType.GAMEOWNER;
        UserAccount mudgamepad = new UserAccount(name,password,email,accountType);
        mudgamepad = creatorRepo.save(mudgamepad);

        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game monopoly = new Game(title,description,category);
        monopoly = gameRepo.save(monopoly);

        Date date = Date.valueOf("2025-01-01");
        Time time = Time.valueOf("11:00:00");
        String location = "Montreal";
        description = "mudgamepad's monopoly event";
        int maxParticipant = 30;
        Event event = new Event(date,time,location,description,maxParticipant,monopoly,mudgamepad);
        event = eventRepo.save(event);

        name = "abc";
        password = "abcde";
        email = "abc@mail.mcgill.ca";
        accountType = AccountType.PLAYER;
        UserAccount abc = new UserAccount(name,password,email,accountType);
        abc = registrantRepo.save(abc);

        ParticipationStatus participationStatus = ParticipationStatus.PENDING;
        EventRegistration eventRegistration = new EventRegistration(new EventRegistration.EventRegistrationKey(abc,event),participationStatus);
        eventRegistration = eventRegistrationRepo.save(eventRegistration);

        EventRegistration eventRegistrationFromDb = eventRegistrationRepo.findByEventRegistrationKey(eventRegistration.getEventRegistrationKey());

        assertNotNull(eventRegistrationFromDb);
        assertNotNull(eventRegistrationFromDb.getEventRegistrationKey());
        assertNotNull(eventRegistrationFromDb.getEventRegistrationKey().getEvent());
        assertEquals(event.getEventID(),eventRegistrationFromDb.getEventRegistrationKey().getEvent().getEventID());
        assertNotNull(eventRegistrationFromDb.getEventRegistrationKey().getRegistrant());
        assertEquals(abc.getUserAccountID(),eventRegistrationFromDb.getEventRegistrationKey().getRegistrant().getUserAccountID());
        assertEquals(eventRegistration.getParticipationStatus(),eventRegistrationFromDb.getParticipationStatus());
    }
}
