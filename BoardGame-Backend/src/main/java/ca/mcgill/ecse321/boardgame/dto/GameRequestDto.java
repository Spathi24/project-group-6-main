package ca.mcgill.ecse321.boardgame.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data transfer object for a game request.
 * Author: Panayiotis Saropoulos
 */

public class GameRequestDto {
    @NotBlank(message = "Game title is required")
    private String title;

    @NotBlank(message = "Game description is required")
    private String description;

    private String category;

    // Required for Jackson deserialization
    public GameRequestDto() {}

    public GameRequestDto(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
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
