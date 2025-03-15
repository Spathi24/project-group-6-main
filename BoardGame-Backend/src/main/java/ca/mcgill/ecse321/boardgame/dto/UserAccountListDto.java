package ca.mcgill.ecse321.boardgame.dto;
import java.util.List;

/**
 * @author Kevin Jiang
 */


public class UserAccountListDto {

    private List<UserAccountResponseDto> userAccountResponseDtoList;

    public UserAccountListDto(List<UserAccountResponseDto> userAccountListDtoList) {
        this.userAccountResponseDtoList = userAccountListDtoList;
    }

    public List<UserAccountResponseDto> getPeople() {
        return userAccountResponseDtoList;
    }

    public void setPeople(List<UserAccountResponseDto> userAccountResponseDtoList) {
        this.userAccountResponseDtoList = userAccountResponseDtoList;
    }
}
