package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.Event;
import ca.mcgill.ecse321.boardgame.model.Game;
import ca.mcgill.ecse321.boardgame.model.UserAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private UserAccountRepository creatorRepo;

    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        eventRepo.deleteAll();
        creatorRepo.deleteAll();
        ;
        gameRepo.deleteAll();
        ;
        ;
    }

    @Test
    public void testCreateAndReadEvent() {

        String name = "mudgamepad";
        String password = "1234567";
        String email = "sibo.jiang@mail.mcgill.ca";
        AccountType accountType = AccountType.GAMEOWNER;
        UserAccount mudgamepad = new UserAccount(name, password, email, accountType);
        mudgamepad = creatorRepo.save(mudgamepad);

        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game monopoly = new Game(title, description, category);
        monopoly = gameRepo.save(monopoly);

        Date date = Date.valueOf("2025-01-01");
        Time time = Time.valueOf("11:00:00");
        String location = "Montreal";
        description = "mudgamepad's monopoly event";
        int maxParticipant = 30;
        Event event = new Event(date, time, location, description, maxParticipant, monopoly, mudgamepad);
        event = eventRepo.save(event);

        Event eventFromDb = eventRepo.findEventByEventID(event.getEventID());

        assertNotNull(eventFromDb);
        assertNotNull(eventFromDb.getCreator());
        assertEquals(event.getCreator().getUserAccountID(), eventFromDb.getCreator().getUserAccountID());
        assertNotNull(eventFromDb.getGameToPlay());
        assertEquals(event.getGameToPlay().getTitle(), eventFromDb.getGameToPlay().getTitle());
        assertEquals(event.getEventID(), eventFromDb.getEventID());
        assertEquals(event.getDate(), eventFromDb.getDate());
        assertEquals(event.getTime(), eventFromDb.getTime());
        assertEquals(event.getLocation(), eventFromDb.getLocation());
        assertEquals(event.getDescription(), eventFromDb.getDescription());
        assertEquals(event.getMaxParticipant(), eventFromDb.getMaxParticipant());

    }
}
