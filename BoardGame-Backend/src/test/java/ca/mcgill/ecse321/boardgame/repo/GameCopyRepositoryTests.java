package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.Game;
import ca.mcgill.ecse321.boardgame.model.GameCopy;
import ca.mcgill.ecse321.boardgame.model.UserAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GameCopyRepositoryTests {

    @Autowired
    private GameCopyRepository gameCopyRepo;

    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        gameCopyRepo.deleteAll();
        userAccountRepo.deleteAll();
        ;
        gameRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadGameCopy() {

        String name = "mudgamepad";
        String password = "1234567";
        String email = "sibo.jiang@mail.mcgill.ca";
        AccountType accountType = AccountType.GAMEOWNER;
        UserAccount mudgamepad = new UserAccount(name, password, email, accountType);
        mudgamepad = userAccountRepo.save(mudgamepad);

        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game monopoly = new Game(title, description, category);
        monopoly = gameRepo.save(monopoly);

        description = "mudgamepad's monopoly";
        GameCopy mudgamepadMonopoly = new GameCopy(new GameCopy.GameCopyKey(mudgamepad, monopoly), description);
        mudgamepadMonopoly = gameCopyRepo.save(mudgamepadMonopoly);

        GameCopy mudgamepadMonopolyFromDb = gameCopyRepo.findGameCopyByGameCopyKey(mudgamepadMonopoly.getGameCopyKey());

        assertNotNull(mudgamepadMonopolyFromDb);
        assertNotNull(mudgamepadMonopolyFromDb.getGameCopyKey());
        assertNotNull(mudgamepadMonopolyFromDb.getGameCopyKey().getOwner());
        assertEquals(mudgamepad.getUserAccountID(),
                mudgamepadMonopolyFromDb.getGameCopyKey().getOwner().getUserAccountID());
        assertNotNull(mudgamepadMonopolyFromDb.getGameCopyKey().getGame());
        assertEquals(monopoly.getTitle(), mudgamepadMonopolyFromDb.getGameCopyKey().getGame().getTitle());
    }

}
