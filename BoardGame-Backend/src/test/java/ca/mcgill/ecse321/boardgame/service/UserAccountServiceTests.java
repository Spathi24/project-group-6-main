package ca.mcgill.ecse321.boardgame.service;

import ca.mcgill.ecse321.boardgame.dto.UserAccountRequestDto;
import ca.mcgill.ecse321.boardgame.exception.BoardGameException;
import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.UserAccount;
import ca.mcgill.ecse321.boardgame.repo.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


/**
 * @author Kevin Jiang
 */


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserAccountServiceTests {

    @Mock
    private UserAccountRepository repo;

    @InjectMocks
    private UserAccountService service;

    private static final String VALID_NAME = "mudgamepad";
    private static final String VALID_PASSWORD = "12345678";
    private static final String VALID_EMAIL = "sibo.jiang@mail.mcgill.ca";
    private static final AccountType VALID_ACCOUNTTYPE = AccountType.GAMEOWNER;


    private static final String NEW_VALID_PASSWORD = "abcdefgh";
    private static final String INVALID_PASSWORD = "123456";

    @Test
    public void testCreateValidUserAccount(){

        UserAccountRequestDto dto = new UserAccountRequestDto(VALID_NAME,VALID_PASSWORD,VALID_EMAIL,VALID_ACCOUNTTYPE);
        when(repo.save(any(UserAccount.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        UserAccount createdUserAccount = service.createUserAccount(dto);

        assertNotNull(createdUserAccount);
        assertEquals(VALID_NAME,createdUserAccount.getName());
        assertEquals(VALID_PASSWORD,createdUserAccount.getPassword());
        assertEquals(VALID_EMAIL,createdUserAccount.getEmail());
        assertEquals(VALID_ACCOUNTTYPE,createdUserAccount.getAccountType());

        verify(repo,times(1)).save(any(UserAccount.class));
    }

    @Test
    public void testFindUserAccountByValidUserAccountID(){

        when(repo.findUserAccountByUserAccountID(42)).thenReturn(new UserAccount(VALID_NAME,VALID_PASSWORD,VALID_EMAIL,VALID_ACCOUNTTYPE));

        UserAccount u = service.findUserAccountByUserAccountID(42);

        assertNotNull(u);
        assertEquals(VALID_NAME,u.getName());
        assertEquals(VALID_PASSWORD,u.getPassword());
        assertEquals(VALID_EMAIL,u.getEmail());
        assertEquals(VALID_ACCOUNTTYPE,u.getAccountType());

    }

    //bug in this test
    @Test
    public void testFindUserAccountThatDoesntExist(){

        BoardGameException e = assertThrows(
                BoardGameException.class,
                () -> service.findUserAccountByUserAccountID(42));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        assertEquals("no userAccount has userAccountID 42.", e.getMessage());

    }

    @Test
    public void testUpdateValidPasswordByUserAccountID(){

        when(repo.findUserAccountByUserAccountID(42)).thenReturn(new UserAccount(VALID_NAME,VALID_PASSWORD,VALID_EMAIL,VALID_ACCOUNTTYPE));
        when(repo.save(any(UserAccount.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        UserAccount u = service.updatePasswordByUserAccountID(42,NEW_VALID_PASSWORD);

        assertNotNull(u);
        assertEquals(VALID_NAME,u.getName());
        assertEquals(NEW_VALID_PASSWORD,u.getPassword());
        assertEquals(VALID_EMAIL,u.getEmail());
        assertEquals(VALID_ACCOUNTTYPE,u.getAccountType());

    }


    //also need invalid password and other updates




}
