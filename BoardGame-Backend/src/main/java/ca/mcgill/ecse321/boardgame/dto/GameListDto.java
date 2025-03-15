package ca.mcgill.ecse321.boardgame.dto;

import java.util.List;

/**
 * Data transfer object for a list of games.
 * Author: Panayiotis Saropoulos
 */

public class GameListDto {
    private List<GameResponseDto> games;

    // Required for Jackson serialization
    public GameListDto() {}

    public GameListDto(List<GameResponseDto> games) {
        this.games = games;
    }

    public List<GameResponseDto> getGames() {
        return games;
    }

    public void setGames(List<GameResponseDto> games) {
        this.games = games;
    }
}