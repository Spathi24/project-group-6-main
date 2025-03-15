package ca.mcgill.ecse321.boardgame.integration;

import ca.mcgill.ecse321.boardgame.dto.ErrorDto;
import ca.mcgill.ecse321.boardgame.dto.UserAccountRequestDto;
import ca.mcgill.ecse321.boardgame.dto.UserAccountResponseDto;
import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.repo.UserAccountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Kevin Jiang
 */


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAccountIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private UserAccountRepository userAccountRepo;

    private long createdUserAccountID;

    private static final String VALID_NAME = "mudgamepad";
    private static final String VALID_PASSWORD = "12345678";
    private static final String VALID_EMAIL = "sibo.jiang@mail.mcgill.ca";
    private static final AccountType VALID_ACCOUNTTYPE = AccountType.GAMEOWNER;

    private static final String INVALID_PASSWORD = "123456";

    private static final String INVALID_EMAIL = "sibo.jiangmail.mcgill.ca";

    private static final String NEW_NAME = "kevin";

    private static final String NEW_PASSWORD = "abcdefgh";

    private static final String NEW_EMAIL = "kevin.jiang@mail.mcgill.ca";

    private static final AccountType NEW_ACCOUNTTYPE = AccountType.PLAYER;

    private static final long INVALID_USERACCOUNTID = 42;

    @BeforeAll
    @AfterAll
    public void clean(){
        userAccountRepo.deleteAll();
    }

    @Test
    @Order(0)
    public void testCreateValidUserAccount(){

        UserAccountRequestDto body = new UserAccountRequestDto(VALID_NAME,VALID_PASSWORD,VALID_EMAIL,VALID_ACCOUNTTYPE);

        ResponseEntity<UserAccountResponseDto> response = client.postForEntity("/UserAccount",body, UserAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getUserAccountID() > 0,"the user account id should be a positive int");
        this.createdUserAccountID = response.getBody().getUserAccountID();
        assertEquals(body.getName(),response.getBody().getName());
        assertEquals(body.getEmail(),response.getBody().getEmail());
        assertEquals(body.getAccountType(),response.getBody().getAccountType());
    }


    @Test
    @Order(1)
    public void testCreateUserAccountWithShortPassword(){

        UserAccountRequestDto body = new UserAccountRequestDto(VALID_NAME,INVALID_PASSWORD,VALID_EMAIL,VALID_ACCOUNTTYPE);

        ResponseEntity<ErrorDto> response = client.postForEntity("/UserAccount",body, ErrorDto.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertNotNull(response.getBody());
        assertIterableEquals(List.of("password must be at least eight characters long"),response.getBody().getErrors());
    }

    @Test
    @Order(2)
    public void testCreateUserAccountWithNullName(){
        UserAccountRequestDto body = new UserAccountRequestDto(null,VALID_PASSWORD,VALID_EMAIL,VALID_ACCOUNTTYPE);

        ResponseEntity<ErrorDto> response = client.postForEntity("/UserAccount",body, ErrorDto.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertNotNull(response.getBody());
        assertIterableEquals(List.of("name is required"),response.getBody().getErrors());
    }

    @Test
    @Order(3)
    public void testCreateUserAccountWithInvalidEmail(){

        UserAccountRequestDto body = new UserAccountRequestDto(VALID_NAME,VALID_PASSWORD,INVALID_EMAIL,VALID_ACCOUNTTYPE);

        ResponseEntity<ErrorDto> response = client.postForEntity("/UserAccount",body, ErrorDto.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertNotNull(response.getBody());
        //"不是一个合法的电子邮件地址" means not a valid email address
        assertIterableEquals(List.of("不是一个合法的电子邮件地址"),response.getBody().getErrors());
    }

    @Test
    @Order(4)
    public void testCreateUserAccountWithNullAccountType(){
        UserAccountRequestDto body = new UserAccountRequestDto(VALID_NAME,VALID_PASSWORD,VALID_EMAIL,null);

        ResponseEntity<ErrorDto> response = client.postForEntity("/UserAccount",body, ErrorDto.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertNotNull(response.getBody());
        assertIterableEquals(List.of("account type is required"),response.getBody().getErrors());
    }


    @Test
    @Order(5)
    public void testFindUserAccountByValidUserAccountID(){

        String url = String.format("/UserAccount/%d",createdUserAccountID);
        ResponseEntity<UserAccountResponseDto> response = client.getForEntity(url, UserAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdUserAccountID,response.getBody().getUserAccountID());
        assertEquals(VALID_NAME,response.getBody().getName());
        assertEquals(VALID_EMAIL,response.getBody().getEmail());
        assertEquals(VALID_ACCOUNTTYPE,response.getBody().getAccountType());

    }

    @Test
    @Order(6)
    public void testFindUserAccountByInvalidUserAccountID(){

        String url = String.format("/UserAccount/%d",INVALID_USERACCOUNTID);
        ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("no userAccount has userAccountID " + INVALID_USERACCOUNTID + ".", body.getErrors().get(0));

    }


    @Test
    @Order(7)
    public void testUpdateUserAccountByValidUserAccountIDAndValue() throws URISyntaxException {

        UserAccountRequestDto body = new UserAccountRequestDto(NEW_NAME,NEW_PASSWORD,NEW_EMAIL,NEW_ACCOUNTTYPE);

        String url = String.format("/UserAccount/%d",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserAccountRequestDto> httpEntity = new HttpEntity<>(body,headers);
        ResponseEntity<UserAccountResponseDto> response = client.exchange(uri, HttpMethod.PUT,httpEntity, UserAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdUserAccountID,response.getBody().getUserAccountID());
        assertEquals(NEW_NAME,response.getBody().getName());
        assertEquals(NEW_EMAIL,response.getBody().getEmail());
        assertEquals(NEW_ACCOUNTTYPE,response.getBody().getAccountType());

    }

    @Test
    @Order(8)
    public void testUpdateUserAccountByInvalidUserAccountIDAndValidValue() throws URISyntaxException {

        UserAccountRequestDto body = new UserAccountRequestDto(NEW_NAME,NEW_PASSWORD,NEW_EMAIL,NEW_ACCOUNTTYPE);

        String url = String.format("/UserAccount/%d",INVALID_USERACCOUNTID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserAccountRequestDto> httpEntity = new HttpEntity<>(body,headers);
        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PUT,httpEntity,ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("no userAccount has userAccountID " + INVALID_USERACCOUNTID + ".", errorBody.getErrors().get(0));


    }


    @Test
    @Order(9)
    public void testUpdateUserAccountByValidUserAccountIDAndShortPassword() throws URISyntaxException {

        UserAccountRequestDto body = new UserAccountRequestDto(NEW_NAME,INVALID_PASSWORD,NEW_EMAIL,NEW_ACCOUNTTYPE);

        String url = String.format("/UserAccount/%d",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserAccountRequestDto> httpEntity = new HttpEntity<>(body,headers);
        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PUT,httpEntity,ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("password must be at least eight characters long", errorBody.getErrors().get(0));

    }

    @Test
    @Order(10)
    public void testUpdateNameByUserAccountIDByValidUserAccountIDAndName() throws URISyntaxException {

        Map<String, Object> map = new HashMap<>();
        map.put("name",VALID_NAME);
        String url = String.format("/UserAccount/%d/name",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        ResponseEntity<UserAccountResponseDto> response = client.exchange(uri, HttpMethod.PATCH,httpEntity,UserAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdUserAccountID,response.getBody().getUserAccountID());
        assertEquals(VALID_NAME,response.getBody().getName());
        assertEquals(NEW_EMAIL,response.getBody().getEmail());
        assertEquals(NEW_ACCOUNTTYPE,response.getBody().getAccountType());


    }


    @Test
    @Order(11)
    public void testUpdateNameByUserAccountIDByInvalidUserAccountID() throws URISyntaxException {
        Map<String, Object> map = new HashMap<>();
        map.put("name",NEW_NAME);
        String url = String.format("/UserAccount/%d/name",INVALID_USERACCOUNTID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PATCH,httpEntity,ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("no userAccount has userAccountID " + INVALID_USERACCOUNTID + ".", errorBody.getErrors().get(0));
    }


    @Test
    @Order(12)
    public void testUpdateNameByUserAccountIDByWrongNumberOfFields() throws URISyntaxException {
        Map<String, Object> map = new HashMap<>();
        map.put("name",NEW_NAME);
        map.put("email",VALID_EMAIL);
        String url = String.format("/UserAccount/%d/name",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PATCH,httpEntity,ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("invalid argument", errorBody.getErrors().get(0));
    }


    @Test
    @Order(13)
    public void testUpdateNameByUserAccountIDByWrongFieldName() throws URISyntaxException{
        Map<String, Object> map = new HashMap<>();
        map.put("naem",NEW_NAME);
        String url = String.format("/UserAccount/%d/name",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PATCH,httpEntity,ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("invalid argument", errorBody.getErrors().get(0));
    }


    @Test
    @Order(14)
    public void testUpdateNameByUserAccountIDByNullName() throws URISyntaxException{
        Map<String, Object> map = new HashMap<>();
        map.put("name",null);
        String url = String.format("/UserAccount/%d/name",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        ResponseEntity<ErrorDto> response = client.exchange(uri, HttpMethod.PATCH,httpEntity,ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("invalid argument", errorBody.getErrors().get(0));
    }

    @Test
    @Order(15)
    public void testUpdateAccountTypeByValidUserAccountIDAndAccountType() throws URISyntaxException{
        Map<String, Object> map = new HashMap<>();
        map.put("accountType",VALID_ACCOUNTTYPE);
        String url = String.format("/UserAccount/%d/accountType",createdUserAccountID);
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        ResponseEntity<UserAccountResponseDto> response = client.exchange(uri, HttpMethod.PATCH,httpEntity,UserAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdUserAccountID,response.getBody().getUserAccountID());
        assertEquals(VALID_NAME,response.getBody().getName());
        assertEquals(NEW_EMAIL,response.getBody().getEmail());
        assertEquals(VALID_ACCOUNTTYPE,response.getBody().getAccountType());
    }

    @Test
    @Order(16)
    public void testLoginByCorrectUserAccountIDAndPassword() throws URISyntaxException {
        String url = String.format("/UserAccount/login/%d/%s",createdUserAccountID,NEW_PASSWORD);
        URI uri = new URI(url);
        ResponseEntity<UserAccountResponseDto> response = client.exchange(uri,HttpMethod.POST,HttpEntity.EMPTY, UserAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdUserAccountID,response.getBody().getUserAccountID());
        assertEquals(VALID_NAME,response.getBody().getName());
        assertEquals(NEW_EMAIL,response.getBody().getEmail());
        assertEquals(VALID_ACCOUNTTYPE,response.getBody().getAccountType());
    }

    @Test
    @Order(17)
    public void testLoginByInvalidUserAccountID() throws URISyntaxException {
        String url = String.format("/UserAccount/login/%d/%s",INVALID_USERACCOUNTID,NEW_PASSWORD);
        URI uri = new URI(url);
        ResponseEntity<ErrorDto> response = client.exchange(uri,HttpMethod.POST,HttpEntity.EMPTY, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("no userAccount has userAccountID " + INVALID_USERACCOUNTID + ".", errorBody.getErrors().get(0));
    }


    @Test
    @Order(18)
    public void testLoginByWrongPassword() throws URISyntaxException {
        String url = String.format("/UserAccount/login/%d/%s",createdUserAccountID,VALID_PASSWORD);
        URI uri = new URI(url);
        ResponseEntity<ErrorDto> response = client.exchange(uri,HttpMethod.POST,HttpEntity.EMPTY, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("wrong password was given.", errorBody.getErrors().get(0));
    }



    @Test
    @Order(19)
    public void testDeleteUserAccountByInvalidUserAccountID() throws URISyntaxException{

        String url = String.format("/UserAccount/%d",INVALID_USERACCOUNTID);
        URI uri = new URI(url);
        ResponseEntity<ErrorDto> response = client.exchange(uri,HttpMethod.DELETE,HttpEntity.EMPTY, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        ErrorDto errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals(1, errorBody.getErrors().size());
        assertEquals("no userAccount has userAccountID " + INVALID_USERACCOUNTID + ".", errorBody.getErrors().get(0));
    }


    @Test
    @Order(20)
    public void testDeleteUserAccountByValidUserAccountID() throws URISyntaxException {

        String url = String.format("/UserAccount/%d",createdUserAccountID);
        URI uri = new URI(url);
        ResponseEntity<Void> r = client.exchange(uri,HttpMethod.DELETE,HttpEntity.EMPTY,Void.class);
        assertNotNull(r);
        assertEquals(HttpStatus.NO_CONTENT,r.getStatusCode());

        ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("no userAccount has userAccountID " + createdUserAccountID + ".", body.getErrors().get(0));

    }






}
