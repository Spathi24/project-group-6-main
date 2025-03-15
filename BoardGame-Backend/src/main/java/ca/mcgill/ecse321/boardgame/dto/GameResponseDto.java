package ca.mcgill.ecse321.boardgame.dto;

import ca.mcgill.ecse321.boardgame.model.Game;

/**
 * Data transfer object for a game response.
 * Author: Panayiotis Saropoulos
 */

public class GameResponseDto {
    private String title;
    private String description;
    private String category;

    // Required for Jackson serialization
    public GameResponseDto() {}

    public GameResponseDto(Game game) {
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.category = game.getCategory();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
