package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GameRepositoryTests {

    @Autowired
    private GameRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadGame() {

        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game monopoly = new Game(title, description, category);
        monopoly = repo.save(monopoly);

        Game monopolyFromDb = repo.findGameByTitle(monopoly.getTitle());

        assertNotNull(monopolyFromDb);
        assertEquals(monopoly.getTitle(), monopolyFromDb.getTitle());
        assertEquals(monopoly.getTitle(), monopolyFromDb.getTitle());
        assertEquals(monopoly.getDescription(), monopolyFromDb.getDescription());
        assertEquals(monopoly.getCategory(), monopolyFromDb.getCategory());
    }
}