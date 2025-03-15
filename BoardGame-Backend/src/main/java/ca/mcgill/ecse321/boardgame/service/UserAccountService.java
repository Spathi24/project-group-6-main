package ca.mcgill.ecse321.boardgame.service;

import ca.mcgill.ecse321.boardgame.dto.UserAccountRequestDto;
import ca.mcgill.ecse321.boardgame.exception.BoardGameException;
import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.GameCopy;
import ca.mcgill.ecse321.boardgame.model.UserAccount;
import ca.mcgill.ecse321.boardgame.repo.GameCopyRepository;
import ca.mcgill.ecse321.boardgame.repo.UserAccountRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Kevin Jiang
 */

@Service
@Validated
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private GameCopyRepository gameCopyRepo;

    @Transactional
    public Iterable<UserAccount> findAllUserAccount(){
        return userAccountRepo.findAll();
    }

    @Transactional
    public UserAccount findUserAccountByUserAccountID(long userAccountID){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        return userAccount;
    }

    @Transactional
    public UserAccount createUserAccount(@Valid UserAccountRequestDto userAccount){
        if (userAccount.getAccountType() == null){
            throw new BoardGameException(HttpStatus.BAD_REQUEST,"account type is required");
        }
        UserAccount userToCreate = new UserAccount(userAccount.getName(),userAccount.getPassword(),userAccount.getEmail(),userAccount.getAccountType());
        return userAccountRepo.save(userToCreate);
    }


    @Transactional
    public UserAccount updateNameByUserAccountID2(long userAccountID, Map<String,String> fields){

        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        if (fields.size() != 1 || fields.get("name") == null){
            throw new BoardGameException(HttpStatus.BAD_REQUEST,"invalid argument");
        }

        //check
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(String.valueOf(fields.get("name")),userAccount.getPassword(),userAccount.getEmail(),userAccount.getAccountType());

        fields.forEach((key,value) ->{
            Field field = ReflectionUtils.findField(UserAccount.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,userAccount,value);
        });
        return userAccountRepo.save(userAccount);
    }


    @Transactional
    public UserAccount updateNameByUserAccountID(long userAccountID,String name){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(name,userAccount.getPassword(),userAccount.getEmail(),userAccount.getAccountType());
        userAccount.setName(name);
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updatePasswordByUserAccountID2(long userAccountID,Map<String, String> fields){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        if (fields.size() != 1 || fields.get("password") == null){
            throw new BoardGameException(HttpStatus.BAD_REQUEST,"invalid argument");
        }

        //check
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(userAccount.getName(),String.valueOf(fields.get("password")),userAccount.getEmail(),userAccount.getAccountType());

        fields.forEach((key,value) ->{
            Field field = ReflectionUtils.findField(UserAccount.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,userAccount,value);
        });
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updatePasswordByUserAccountID(long userAccountID,String password){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(userAccount.getName(),password,userAccount.getEmail(),userAccount.getAccountType());
        userAccount.setPassword(password);
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updateEmailByUserAccountID2(long userAccountID,Map<String, String> fields){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        if (fields.size() != 1 || fields.get("email") == null){
            throw new BoardGameException(HttpStatus.BAD_REQUEST,"invalid argument");
        }

        //check
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(userAccount.getName(),userAccount.getPassword(),String.valueOf(fields.get("email")),userAccount.getAccountType());

        fields.forEach((key,value) ->{
            Field field = ReflectionUtils.findField(UserAccount.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,userAccount,value);
        });
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updateEmailByUserAccountID(long userAccountID,String email){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(userAccount.getName(),userAccount.getPassword(),email,userAccount.getAccountType());
        userAccount.setEmail(email);
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updateAccountTypeByUserAccountID2(long userAccountID, Map<String, AccountType> fields){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        if (fields.size() != 1 || fields.get("accountType") == null) {
            throw new BoardGameException(HttpStatus.BAD_REQUEST,"invalid argument");
        }

        if (userAccount.getAccountType().equals(AccountType.GAMEOWNER) && AccountType.valueOf(String.valueOf(fields.get("accountType"))).equals(AccountType.PLAYER)){
            for (GameCopy gameCopy : gameCopyRepo.findAll()){
                if (gameCopy.getGameCopyKey().getOwner().getUserAccountID() == userAccountID){
                    throw new BoardGameException(HttpStatus.FORBIDDEN,"game owner need to delete all his game copies before become player");
                }
            }
        }

        //check
        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(userAccount.getName(),userAccount.getPassword(),userAccount.getEmail(),AccountType.valueOf(String.valueOf(fields.get("accountType"))));

        fields.forEach((key,value) ->{
            Field field = ReflectionUtils.findField(UserAccount.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,userAccount,value);
        });
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updateAccountTypeByUserAccountID(long userAccountID, AccountType accountType){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }

        if (accountType == null){
            throw new BoardGameException(HttpStatus.BAD_REQUEST,"account type is required");
        }
        if (userAccount.getAccountType().equals(AccountType.GAMEOWNER) && accountType.equals(AccountType.PLAYER)){
            for (GameCopy gameCopy : gameCopyRepo.findAll()){
                if (gameCopy.getGameCopyKey().getOwner().getUserAccountID() == userAccountID){
                    throw new BoardGameException(HttpStatus.FORBIDDEN,"game owner need to delete all his game copies before become player");
                }
            }
        }

        UserAccountRequestDto userToUpdate = new UserAccountRequestDto(userAccount.getName(),userAccount.getPassword(),userAccount.getEmail(),accountType);
        userAccount.setAccountType(accountType);
        return userAccountRepo.save(userAccount);
    }

    @Transactional
    public UserAccount updateUserAccountByUserAccountID(long userAccountID,@Valid UserAccountRequestDto userAccount){
        updateNameByUserAccountID(userAccountID,userAccount.getName());
        updatePasswordByUserAccountID(userAccountID,userAccount.getPassword());
        updateEmailByUserAccountID(userAccountID,userAccount.getEmail());
        UserAccount updatedUserAccount = updateAccountTypeByUserAccountID(userAccountID,userAccount.getAccountType());
        return updatedUserAccount;
    }

    @Transactional
    public void deleteUserAccountByUserAccountID(long userAccountID){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        userAccountRepo.delete(userAccount);
    }

    @Transactional
    public UserAccount login(long userAccountID,String password){
        UserAccount userAccount = userAccountRepo.findUserAccountByUserAccountID(userAccountID);
        if (userAccount == null) {
            throw new BoardGameException(HttpStatus.NOT_FOUND,"no userAccount has userAccountID " + userAccountID + ".");
        }
        if (!password.equals(userAccount.getPassword())){
            throw new BoardGameException(HttpStatus.UNAUTHORIZED,"wrong password was given.");
        }
        return userAccount;
    }
}
