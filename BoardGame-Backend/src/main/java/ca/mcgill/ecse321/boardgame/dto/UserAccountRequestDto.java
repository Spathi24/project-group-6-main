package ca.mcgill.ecse321.boardgame.dto;

import ca.mcgill.ecse321.boardgame.model.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Kevin Jiang
 */

public class UserAccountRequestDto {


    @NotBlank(message = "name is required")
    private String name;

    @Size(min = 8, message = "password must be at least eight characters long")
    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "email address is required")
    @Email
    private String email;

    private AccountType accountType;

    public UserAccountRequestDto(String name,String password,String email,AccountType accountType){
        this.name = name;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public AccountType getAccountType(){
        return accountType;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }

}
