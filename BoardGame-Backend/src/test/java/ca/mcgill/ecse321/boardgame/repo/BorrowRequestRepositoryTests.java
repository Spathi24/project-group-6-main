package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BorrowRequestRepositoryTests {

    @Autowired
    private BorrowRequestRepository borrowRequestRepo;

    @Autowired
    private UserAccountRepository borrowerRepo;

    @Autowired
    private GameCopyRepository gameCopyRepo;

    @Autowired
    private UserAccountRepository ownerRepo;

    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        borrowRequestRepo.deleteAll();
        gameCopyRepo.deleteAll();
        ;
        ;
        borrowerRepo.deleteAll();
        ownerRepo.deleteAll();
        gameRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadBorrowRequest() {

        String name = "mudgamepad";
        String password = "1234567";
        String email = "sibo.jiang@mail.mcgill.ca";
        AccountType accountType = AccountType.GAMEOWNER;
        UserAccount mudgamepad = new UserAccount(name, password, email, accountType);
        mudgamepad = ownerRepo.save(mudgamepad);

        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game monopoly = new Game(title, description, category);
        monopoly = gameRepo.save(monopoly);

        description = "mudgamepad's monopoly";
        GameCopy mudgamepadMonopoly = new GameCopy(new GameCopy.GameCopyKey(mudgamepad, monopoly), description);
        mudgamepadMonopoly = gameCopyRepo.save(mudgamepadMonopoly);

        name = "abc";
        password = "abcde";
        email = "abc@mail.mcgill.ca";
        accountType = AccountType.PLAYER;
        UserAccount abc = new UserAccount(name, password, email, accountType);
        abc = borrowerRepo.save(abc);

        RequestStatus requestStatus = RequestStatus.PENDING;
        Date requestDate = Date.valueOf("2025-01-01");
        Date decisionDate = null;
        Date startDate = Date.valueOf("2025-01-07");
        Date endDate = Date.valueOf("2025-01-14");
        BorrowRequest borrowRequest = new BorrowRequest(requestStatus, (java.sql.Date) requestDate, decisionDate,
                (java.sql.Date) startDate, (java.sql.Date) endDate, abc, mudgamepadMonopoly);
        borrowRequest = borrowRequestRepo.save(borrowRequest);

        BorrowRequest borrowRequestFromDb = borrowRequestRepo.findBorrowRequestById(borrowRequest.getId());

        assertNotNull(borrowRequestFromDb);
        assertNotNull(borrowRequestFromDb.getBorrower());
        assertEquals(abc.getUserAccountID(), borrowRequestFromDb.getBorrower().getUserAccountID());
        assertNotNull(borrowRequestFromDb.getGameToBorrow());
        assertNotNull(borrowRequestFromDb.getGameToBorrow().getGameCopyKey());
        assertNotNull(borrowRequestFromDb.getGameToBorrow().getGameCopyKey().getOwner());
        assertEquals(mudgamepad.getUserAccountID(),
                borrowRequestFromDb.getGameToBorrow().getGameCopyKey().getOwner().getUserAccountID());
        assertNotNull(borrowRequestFromDb.getGameToBorrow().getGameCopyKey().getGame());
        assertEquals(monopoly.getTitle(), borrowRequestFromDb.getGameToBorrow().getGameCopyKey().getGame().getTitle());
        assertEquals(borrowRequest.getId(), borrowRequestFromDb.getId());
        assertEquals(borrowRequest.getRequestStatus(), borrowRequestFromDb.getRequestStatus());
        assertEquals(borrowRequest.getRequestDate(), borrowRequestFromDb.getRequestDate());
        assertEquals(borrowRequest.getDecisionDate(), borrowRequestFromDb.getDecisionDate());
        assertEquals(borrowRequest.getStartDate(), borrowRequestFromDb.getStartDate());
        assertEquals(borrowRequest.getEndDate(), borrowRequestFromDb.getEndDate());

    }

}
