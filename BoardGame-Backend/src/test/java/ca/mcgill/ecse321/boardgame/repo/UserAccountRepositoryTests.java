package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.UserAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserAccountRepositoryTests {

    @Autowired
    private UserAccountRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase(){
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadUserAccount(){

        String name = "mudgamepad";
        String password = "1234567";
        String email = "sibo.jiang@mail.mcgill.ca";
        AccountType accountType = AccountType.GAMEOWNER;
        UserAccount mudgamepad = new UserAccount(name,password,email,accountType);
        mudgamepad = repo.save(mudgamepad);

        UserAccount mudgamepadFromDb = repo.findUserAccountByUserAccountID(mudgamepad.getUserAccountID());

        assertNotNull(mudgamepadFromDb);
        assertEquals(mudgamepad.getUserAccountID(),mudgamepadFromDb.getUserAccountID());
        assertEquals(mudgamepad.getName(),mudgamepadFromDb.getName());
        assertEquals(mudgamepad.getPassword(),mudgamepadFromDb.getPassword());
        assertEquals(mudgamepad.getEmail(),mudgamepadFromDb.getEmail());
        assertEquals(mudgamepad.getAccountType(),mudgamepadFromDb.getAccountType());


    }
}
