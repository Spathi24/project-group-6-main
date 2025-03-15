package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.GameCopy;
import ca.mcgill.ecse321.boardgame.model.UserAccount;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GameCopyRepository extends CrudRepository<GameCopy, GameCopy.GameCopyKey> {
    public GameCopy findGameCopyByGameCopyKey(GameCopy.GameCopyKey gameCopyKey);

    public List<GameCopy> findAllByGameCopyKeyOwner(UserAccount owner);
}
