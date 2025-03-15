package ca.mcgill.ecse321.boardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.boardgame.exception.ResourceNotFoundException;
import ca.mcgill.ecse321.boardgame.model.Game;
import ca.mcgill.ecse321.boardgame.repo.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game getGameByTitle(String title) {
        Game game = gameRepository.findGameByTitle(title);
        if (game == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,
                    "Game with title " + title + " not found");
        }
        return game;
    }
}
