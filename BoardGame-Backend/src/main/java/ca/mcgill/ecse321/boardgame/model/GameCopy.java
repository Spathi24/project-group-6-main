package ca.mcgill.ecse321.boardgame.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "GameCopy")
public class GameCopy {

    @EmbeddedId
    private GameCopyKey gameCopyKey;

    private String description;

    private GameStatus status;

    protected GameCopy() {
    }

    public GameCopy(GameCopyKey gameCopyKey, String description) {
        this.gameCopyKey = gameCopyKey;
        this.description = description;
        status = GameStatus.AVAILABLE;
    }

    public GameCopyKey getGameCopyKey() {
        return gameCopyKey;
    }

    public String getDescription() {
        return description;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @Embeddable
    public static class GameCopyKey implements Serializable {

        @ManyToOne
        private UserAccount owner;

        @ManyToOne
        private Game game;

        public GameCopyKey() {
        }

        public GameCopyKey(UserAccount owner, Game game) {
            this.owner = owner;
            this.game = game;
        }

        public UserAccount getOwner() {
            return owner;
        }

        public Game getGame() {
            return game;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof GameCopyKey)) {
                return false;
            }
            GameCopyKey that = (GameCopyKey) obj;
            return Objects.equals(this.owner.getUserAccountID(), that.owner.getUserAccountID())
                    && this.game.getTitle() == that.game.getTitle();
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.owner.getUserAccountID(), this.game.getTitle());
        }
    }

}
