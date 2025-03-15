package ca.mcgill.ecse321.boardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import ca.mcgill.ecse321.boardgame.dto.GameRequestDto;
import ca.mcgill.ecse321.boardgame.exception.BoardGameException;
import ca.mcgill.ecse321.boardgame.exception.ResourceNotFoundException;
import ca.mcgill.ecse321.boardgame.model.Game;
import ca.mcgill.ecse321.boardgame.repo.GameRepository;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing games.
 * Author: Panayiotis Saropulos
 */
@Service
@Validated
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Retrieves a game by its title.
     *
     * @param title the title of the game
     * @return the Game object
     * @throws ResourceNotFoundException if the game is not found
     */
    @Transactional
    public Game getGameByTitle(String title) {
        Game game = gameRepository.findGameByTitle(title);
        if (game == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,
                    "Game with title '" + title + "' not found");
        }
        return game;
    }

    /**
     * Retrieves all games.
     *
     * @return a list of all Game objects
     */
    @Transactional
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    /**
     * Creates a new game.
     *
     * @param gameRequestDto the details of the game to be created
     * @return the created Game object
     * @throws BoardGameException if a game with the same title already exists
     */
    @Transactional
    public Game createGame(@Valid GameRequestDto gameRequestDto) {
        // Check if game with same title already exists
        if (gameRepository.findGameByTitle(gameRequestDto.getTitle()) != null) {
            throw new BoardGameException(HttpStatus.CONFLICT,
                    "Game with title '" + gameRequestDto.getTitle() + "' already exists");
        }

        Game game = new Game(
                gameRequestDto.getTitle(),
                gameRequestDto.getDescription(),
                gameRequestDto.getCategory()
        );

        return gameRepository.save(game);
    }

    /**
     * Updates an existing game's details.
     *
     * @param title the title of the game to update
     * @param gameRequestDto the new details for the game
     * @return the updated Game object
     * @throws ResourceNotFoundException if the game is not found
     */
    @Transactional
    public Game updateGame(String title, @Valid GameRequestDto gameRequestDto) {
        Game game = getGameByTitle(title);

        // Only update description and category, title is the ID and cannot be changed
        game.setDescription(gameRequestDto.getDescription());

        // Update category by reflection since there's no setter in the Game class
        try {
            java.lang.reflect.Field categoryField = Game.class.getDeclaredField("category");
            categoryField.setAccessible(true);
            categoryField.set(game, gameRequestDto.getCategory());
        } catch (Exception e) {
            throw new BoardGameException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to update game category: " + e.getMessage());
        }

        return gameRepository.save(game);
    }

    /**
     * Deletes a game by its title.
     *
     * @param title the title of the game to delete
     * @throws ResourceNotFoundException if the game is not found
     */
    @Transactional
    public void deleteGame(String title) {
        Game game = getGameByTitle(title);
        gameRepository.delete(game);
    }
}