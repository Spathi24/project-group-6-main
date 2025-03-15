package ca.mcgill.ecse321.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.boardgame.dto.GameListDto;
import ca.mcgill.ecse321.boardgame.dto.GameRequestDto;
import ca.mcgill.ecse321.boardgame.dto.GameResponseDto;
import ca.mcgill.ecse321.boardgame.model.Game;
import ca.mcgill.ecse321.boardgame.service.GameService;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * RESTful API controller for managing games.
 * Author: Panayiotis Saropoulos
 */
@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Retrieves all games.
     *
     * @return a list of all games
     */
    @GetMapping
    public GameListDto getAllGames() {
        List<GameResponseDto> gameResponseDtos = new ArrayList<>();
        for (Game game : gameService.getAllGames()) {
            gameResponseDtos.add(new GameResponseDto(game));
        }
        return new GameListDto(gameResponseDtos);
    }

    /**
     * Retrieves a game by its title.
     *
     * @param title the title of the game
     * @return the GameResponseDto
     */
    @GetMapping("/{title}")
    public GameResponseDto getGameByTitle(@PathVariable String title) {
        Game game = gameService.getGameByTitle(title);
        return new GameResponseDto(game);
    }

    /**
     * Creates a new game.
     *
     * @param gameRequestDto the details of the game to be created
     * @return the created GameResponseDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponseDto createGame(@RequestBody @Valid GameRequestDto gameRequestDto) {
        Game createdGame = gameService.createGame(gameRequestDto);
        return new GameResponseDto(createdGame);
    }

    /**
     * Updates a game's details.
     *
     * @param title the title of the game to update
     * @param gameRequestDto the new details for the game
     * @return the updated GameResponseDto
     */
    @PutMapping("/{title}")
    public GameResponseDto updateGame(@PathVariable String title,
                                      @RequestBody @Valid GameRequestDto gameRequestDto) {
        Game updatedGame = gameService.updateGame(title, gameRequestDto);
        return new GameResponseDto(updatedGame);
    }

    /**
     * Deletes a game by its title.
     *
     * @param title the title of the game to delete
     */
    @DeleteMapping("/{title}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable String title) {
        gameService.deleteGame(title);
    }
}
