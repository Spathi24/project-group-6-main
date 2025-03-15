package ca.mcgill.ecse321.boardgame.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Game model
 * @author Panayiotis Saropoulos
 */
public class GameTests {

    @Test
    public void testGameCreation() {
        // Create a game with constructor
        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game game = new Game(title, description, category);

        // Verify all attributes are correctly set
        assertEquals(title, game.getTitle());
        assertEquals(description, game.getDescription());
        assertEquals(category, game.getCategory());
    }

    @Test
    public void testGameSetters() {
        // Create a game
        Game game = new Game("Risk", "World domination", "Strategy");

        // Update attributes using setters (title has no setter)
        String newDescription = "Conquer the world";
        String newCategory = "War Strategy";

        game.setDescription(newDescription);
        game.setCategory(newCategory);

        // Verify attributes are updated
        assertEquals("Risk", game.getTitle()); // Title remains unchanged
        assertEquals(newDescription, game.getDescription());
        assertEquals(newCategory, game.getCategory());
    }

    @Test
    public void testNullValues() {
        // Title is the @Id and cannot be null
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(null, "Description", "Category");
        });
    }

    @Test
    public void testEmptyValues() {
        String emptyString = "";
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(emptyString, emptyString, emptyString);
        });
    }

    @Test
    public void testToString() {
        Game game = new Game("Catan", "Build settlements", "Strategy");
        assertTrue(game.toString().contains("Catan"));
    }

    @Test
    public void testGameEquality() {
        Game game1 = new Game("Chess", "Classic strategy", "Abstract");
        Game game2 = new Game("Chess", "Different description", "Strategy");
        Game game3 = new Game("Checkers", "Jump over pieces", "Abstract");

        assertEquals(game1, game2); // Same title
        assertNotEquals(game1, game3);
    }

    @Test
    public void testHashCode() {
        Game game1 = new Game("Chess", "Classic strategy", "Abstract");
        Game game2 = new Game("Chess", "Different description", "Strategy");
        assertEquals(game1.hashCode(), game2.hashCode());
    }
}