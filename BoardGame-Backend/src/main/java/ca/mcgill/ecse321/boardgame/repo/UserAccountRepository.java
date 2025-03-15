package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount,Long> {
    public UserAccount findUserAccountByUserAccountID(long userAccountID);

    //@Modifying
    //@Query("update UserAccount u set u.name = ?1,u.password = ?2,u.email = ?3,u.accountType = ?4 where u.userAccountID = userAccountID")
    //public int setUserAccountByUserAccountID(String name, String password, String email, AccountType accountType, long userAccountID);

    //@Modifying
    //@Query("delete from UserAccount u where u.userAccountID = ?1")
    //public void deleteUserAccountByUserAccountID(long userAccountID);
}
