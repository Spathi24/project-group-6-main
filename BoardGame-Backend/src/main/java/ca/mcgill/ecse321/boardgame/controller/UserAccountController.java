package ca.mcgill.ecse321.boardgame.controller;

import ca.mcgill.ecse321.boardgame.dto.UserAccountListDto;
import ca.mcgill.ecse321.boardgame.dto.UserAccountRequestDto;
import ca.mcgill.ecse321.boardgame.dto.UserAccountResponseDto;
import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.UserAccount;
import ca.mcgill.ecse321.boardgame.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin Jiang
 */

@RestController
//@CrossOrigin(origins = "http://localhost:5432/")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/UserAccount")
    public UserAccountListDto findAllUserAccount(){
        List<UserAccountResponseDto> userAccountResponseDtoList = new ArrayList<UserAccountResponseDto>();
        for (UserAccount model: userAccountService.findAllUserAccount()){
            userAccountResponseDtoList.add(new UserAccountResponseDto(model));
        }
        return new UserAccountListDto(userAccountResponseDtoList);
    }

    @GetMapping("/UserAccount/{userAccountID}")
    public UserAccountResponseDto findUserAccountByUserAccountID(@PathVariable long userAccountID){
        UserAccount userAccount = userAccountService.findUserAccountByUserAccountID(userAccountID);
        return new UserAccountResponseDto(userAccount);
    }

    @PostMapping("/UserAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccountResponseDto createUserAccount(@RequestBody UserAccountRequestDto userAccount){
        UserAccount createdUserAccount = userAccountService.createUserAccount(userAccount);
        return new UserAccountResponseDto(createdUserAccount);
    }

    @PatchMapping("/UserAccount/{userAccountID}/name")
    @ResponseStatus(HttpStatus.OK)
    public UserAccountResponseDto updateNameByUserAccountID2(@PathVariable long userAccountID,@RequestBody Map<String, String> fields){

        UserAccount updatedUserAccount = userAccountService.updateNameByUserAccountID2(userAccountID,fields);
        return new UserAccountResponseDto(updatedUserAccount);

    }


    @PatchMapping("/UserAccount/{userAccountID}/password")
    @ResponseStatus(HttpStatus.OK)
    public UserAccountResponseDto updatePasswordByUserAccountID(@PathVariable long userAccountID,@RequestBody Map<String, String> fields){
        UserAccount updatedUserAccount = userAccountService.updatePasswordByUserAccountID2(userAccountID,fields);
        return new UserAccountResponseDto(updatedUserAccount);
    }


    @PatchMapping("/UserAccount/{userAccountID}/email")
    @ResponseStatus(HttpStatus.OK)
    public UserAccountResponseDto updateEmailByUserAccountID(@PathVariable long userAccountID,@RequestBody Map<String, String> fields){
        UserAccount updatedUserAccount = userAccountService.updateEmailByUserAccountID2(userAccountID,fields);
        return new UserAccountResponseDto(updatedUserAccount);
    }


    @PatchMapping("/UserAccount/{userAccountID}/accountType")
    @ResponseStatus(HttpStatus.OK)
    public UserAccountResponseDto updateAccountTypeByUserAccountID(@PathVariable long userAccountID,@RequestBody Map<String, AccountType> fields){
        UserAccount updatedUserAccount = userAccountService.updateAccountTypeByUserAccountID2(userAccountID,fields);
        return new UserAccountResponseDto(updatedUserAccount);
    }

    @PutMapping("/UserAccount/{userAccountID}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccountResponseDto updateUserAccountByUserAccountID(@PathVariable long userAccountID,@RequestBody UserAccountRequestDto userAccount){
        UserAccount updatedUserAccount = userAccountService.updateUserAccountByUserAccountID(userAccountID,userAccount);
        return new UserAccountResponseDto(updatedUserAccount);
    }

    @DeleteMapping("/UserAccount/{userAccountID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAccountByUserAccountID(@PathVariable long userAccountID){
        userAccountService.deleteUserAccountByUserAccountID(userAccountID);
    }

    @PostMapping("/UserAccount/login/{userAccountID}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccountResponseDto login(@PathVariable long userAccountID,@PathVariable String password){
        UserAccount loginUserAccount = userAccountService.login(userAccountID,password);
        return new UserAccountResponseDto(loginUserAccount);
    }

}
