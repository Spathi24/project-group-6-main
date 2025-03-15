package ca.mcgill.ecse321.boardgame.dto;

import ca.mcgill.ecse321.boardgame.model.GameCopy;
import ca.mcgill.ecse321.boardgame.model.GameStatus;

public class GameCopyResponseDto {
    private String title;
    private String description;
    private long owner;
    private GameStatus status;

    public GameCopyResponseDto(GameCopy gameCopy) {
        this.title = gameCopy.getGameCopyKey().getGame().getTitle();
        this.description = gameCopy.getDescription();
        this.owner = gameCopy.getGameCopyKey().getOwner().getUserAccountID();
        this.status = gameCopy.getStatus();
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

    public GameStatus getStatus() {
        return status;
    }
}
