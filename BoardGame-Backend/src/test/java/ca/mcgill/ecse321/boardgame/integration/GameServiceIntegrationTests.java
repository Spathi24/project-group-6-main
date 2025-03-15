package ca.mcgill.ecse321.boardgame.integration;

import ca.mcgill.ecse321.boardgame.BoardgameApplication;
import ca.mcgill.ecse321.boardgame.dto.ErrorDto;
import ca.mcgill.ecse321.boardgame.dto.GameRequestDto;
import ca.mcgill.ecse321.boardgame.dto.GameResponseDto;
import ca.mcgill.ecse321.boardgame.repo.GameRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Game service.
 * @author Panayiotis Saropoulos
 */
@SpringBootTest(classes = BoardgameApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameRepository gameRepo;

    // Test data
    private static final String VALID_TITLE = "Monopoly";
    private static final String VALID_DESCRIPTION = "Classic property trading game";
    private static final String VALID_CATEGORY = "Family";

    private static final String UPDATED_DESCRIPTION = "Classic property trading game with economic focus";
    private static final String UPDATED_CATEGORY = "Strategy";

    private static final String NEW_TITLE = "Catan";
    private static final String NEW_DESCRIPTION = "Build settlements and trade resources";
    private static final String NEW_CATEGORY = "Strategy";

    private static final String NONEXISTENT_TITLE = "NonexistentGame";

    @BeforeAll
    @AfterAll
    public void cleanDatabase() {
        gameRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidGame() {
        GameRequestDto requestDto = new GameRequestDto(VALID_TITLE, VALID_DESCRIPTION, VALID_CATEGORY);

        ResponseEntity<GameResponseDto> response = client.postForEntity("/games", requestDto, GameResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(VALID_TITLE, response.getBody().getTitle());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_CATEGORY, response.getBody().getCategory());
    }

    @Test
    @Order(2)
    public void testCreateGameWithExistingTitle() {
        GameRequestDto requestDto = new GameRequestDto(VALID_TITLE, "Another description", "Another category");

        ResponseEntity<ErrorDto> response = client.postForEntity("/games", requestDto, ErrorDto.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getErrors().isEmpty());
        assertTrue(response.getBody().getErrors().get(0).contains("already exists"));
    }

    @Test
    @Order(3)
    public void testCreateGameWithEmptyTitle() {
        GameRequestDto requestDto = new GameRequestDto("", VALID_DESCRIPTION, VALID_CATEGORY);

        ResponseEntity<ErrorDto> response = client.postForEntity("/games", requestDto, ErrorDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getErrors().isEmpty());
    }

    @Test
    @Order(4)
    public void testGetGameByValidTitle() {
        ResponseEntity<GameResponseDto> response = client.getForEntity("/games/" + VALID_TITLE, GameResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(VALID_TITLE, response.getBody().getTitle());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_CATEGORY, response.getBody().getCategory());
    }

    @Test
    @Order(5)
    public void testGetGameByInvalidTitle() {
        ResponseEntity<ErrorDto> response = client.getForEntity("/games/" + NONEXISTENT_TITLE, ErrorDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getErrors().isEmpty());
        assertTrue(response.getBody().getErrors().get(0).contains("not found"));
    }

    @Test
    @Order(6)
    public void testGetAllGames() {
        // Create another game
        GameRequestDto requestDto = new GameRequestDto(NEW_TITLE, NEW_DESCRIPTION, NEW_CATEGORY);
        client.postForEntity("/games", requestDto, GameResponseDto.class);

        // Get all games
        ResponseEntity<GameResponseDto[]> response = client.getForEntity("/games", GameResponseDto[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length); // We should have 2 games now

        // Verify both games are in the list
        boolean foundMonopoly = false;
        boolean foundCatan = false;

        for (GameResponseDto game : response.getBody()) {
            if (VALID_TITLE.equals(game.getTitle())) {
                foundMonopoly = true;
            } else if (NEW_TITLE.equals(game.getTitle())) {
                foundCatan = true;
            }
        }

        assertTrue(foundMonopoly && foundCatan);
    }

    @Test
    @Order(7)
    public void testUpdateGameWithValidTitle() throws URISyntaxException {
        GameRequestDto requestDto = new GameRequestDto(VALID_TITLE, UPDATED_DESCRIPTION, UPDATED_CATEGORY);

        URI uri = new URI("/games/" + VALID_TITLE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GameRequestDto> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<GameResponseDto> response = client.exchange(uri, HttpMethod.PUT, entity, GameResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(VALID_TITLE, response.getBody().getTitle());
        assertEquals(UPDATED_DESCRIPTION, response.getBody().getDescription());
        assertEquals(UPDATED_CATEGORY, response.getBody().getCategory());
    }

    @Test
    @Order(8)
    public void testUpdateGameWithInvalidTitle() throws URISyntaxException {
        GameRequestDto requestDto = new GameRequestDto(NONEXISTENT_TITLE, UPDATED_DESCRIPTION, UPDATED_CATEGORY);

        URI uri = new URI("/games/" + NONEXISTENT_TITLE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GameRequestDto> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PUT, entity, ErrorDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getErrors().isEmpty());
        assertTrue(response.getBody().getErrors().get(0).contains("not found"));
    }

    @Test
    @Order(9)
    public void testDeleteGameWithValidTitle() throws URISyntaxException {
        URI uri = new URI("/games/" + NEW_TITLE);
        HttpEntity<Void> entity = new HttpEntity<>(null);

        ResponseEntity<Void> deleteResponse = client.exchange(uri, HttpMethod.DELETE, entity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Verify the game is deleted
        ResponseEntity<ErrorDto> getResponse = client.getForEntity("/games/" + NEW_TITLE, ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    @Order(10)
    public void testDeleteGameWithInvalidTitle() throws URISyntaxException {
        URI uri = new URI("/games/" + NONEXISTENT_TITLE);
        HttpEntity<Void> entity = new HttpEntity<>(null);

        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.DELETE, entity, ErrorDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getErrors().isEmpty());
        assertTrue(response.getBody().getErrors().get(0).contains("not found"));
    }
}