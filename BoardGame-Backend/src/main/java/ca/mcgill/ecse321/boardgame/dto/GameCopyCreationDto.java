package ca.mcgill.ecse321.boardgame.dto;

import jakarta.validation.constraints.NotBlank;

public class GameCopyCreationDto {
    @NotBlank(message = "The game title is required")
    private String title;
    @NotBlank(message = "The game description is required")
    private String description;
    @NotBlank(message = "The owner id is required")
    private long owner;

    public GameCopyCreationDto(String title, String description, long owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }
}
