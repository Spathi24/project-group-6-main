package ca.mcgill.ecse321.boardgame.model;

import jakarta.persistence.*;

@Entity(name = "UserAccount")
public class UserAccount{

    @Id
    @GeneratedValue
    private long userAccountID;

    private String name;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    protected UserAccount(){}

    public UserAccount(String name,String password,String email,AccountType accountType){
        this.name = name;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
    }


    public Long getUserAccountID(){
        return userAccountID;
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

    public void setName(String name){this.name = name;}

    public void setPassword(String password){this.password = password;}

    public void setEmail(String email){this.email = email;}

    public void setAccountType(AccountType accountType){this.accountType = accountType;}

}
