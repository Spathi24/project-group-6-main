package ca.mcgill.ecse321.boardgame.dto;

import ca.mcgill.ecse321.boardgame.model.AccountType;
import ca.mcgill.ecse321.boardgame.model.UserAccount;

/**
 * @author Kevin Jiang
 */

public class UserAccountResponseDto {

    private Long userAccountID;
    private String name;
    private String email;

    private AccountType accountType;

    @SuppressWarnings("unused")
    private UserAccountResponseDto(){}

    public UserAccountResponseDto(UserAccount model){
        this.userAccountID = model.getUserAccountID();
        this.name = model.getName();
        this.email = model.getEmail();
        this.accountType = model.getAccountType();
    }

    public Long getUserAccountID(){
        return userAccountID;
    }

    public String getName() {
        return name;
    }

    public String getEmail(){
        return email;
    }

    public AccountType getAccountType(){return  accountType;}

    public void setUserAccountID(long userAccountID){
        this.userAccountID = userAccountID;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }


}
