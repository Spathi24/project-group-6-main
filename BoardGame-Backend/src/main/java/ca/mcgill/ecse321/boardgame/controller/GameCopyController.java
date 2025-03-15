package ca.mcgill.ecse321.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.boardgame.dto.GameCopyCreationDto;
import ca.mcgill.ecse321.boardgame.dto.GameCopyResponseDto;
import ca.mcgill.ecse321.boardgame.model.GameCopy;
import ca.mcgill.ecse321.boardgame.service.GameCopyService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gamecopies")
public class GameCopyController {

    @Autowired
    private GameCopyService gameCopyService;

    /**
     * Creates a new game copy for the specified user.
     *
     * @param userAccountId       the ID of the user creating the game copy
     * @param gameCopyCreationDto the details of the game copy to be created
     * @return the created GameCopy
     */
    @PostMapping("/{userAccountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public GameCopyResponseDto createGameCopy(@PathVariable long userAccountId,
            @RequestBody @Valid GameCopyCreationDto gameCopyCreationDto) {
        gameCopyCreationDto.setOwner(userAccountId);
        GameCopy createdGameCopy = gameCopyService.createGameCopy(gameCopyCreationDto);
        return new GameCopyResponseDto(createdGameCopy);
    }

    /**
     * Updates the status of a game copy for the specified user.
     *
     * @param userAccountId the ID of the user updating the game copy status
     * @param title         the title of the game copy
     * @param status        the new status of the game copy
     */
    @PutMapping("/{userAccountId}/status/{title}")
    public void setStatus(@PathVariable long userAccountId, @PathVariable String title,
            @RequestParam String status) {
        gameCopyService.updateStatus(title, userAccountId, status);
    }

    /**
     * Updates the description of a game copy for the specified user.
     *
     * @param userAccountId  the ID of the user updating the game copy description
     * @param title          the title of the game copy
     * @param newDescription the new description of the game copy
     */
    @PutMapping("/{userAccountId}/description/{title}")
    public void updateDescription(@PathVariable long userAccountId, @PathVariable String title,
            @RequestParam String newDescription) {
        gameCopyService.updateDescription(title, userAccountId, newDescription);
    }

    /**
     * Retrieves all game copies for the specified user.
     *
     * @param userAccountId the ID of the user whose game copies are to be retrieved
     * @return a list of GameCopy objects
     */
    @GetMapping("/{userAccountId}")
    public List<GameCopyResponseDto> getUserGameCopies(@PathVariable long userAccountId) {
        return gameCopyService.getUserGameCopies(userAccountId);
    }

    /**
     * Deletes a game copy for the specified user.
     *
     * @param userAccountId the ID of the user deleting the game copy
     * @param title         the title of the game copy to be deleted
     */
    @DeleteMapping("/{userAccountId}/{title}")
    public void deleteGameCopy(@PathVariable long userAccountId, @PathVariable String title) {
        gameCopyService.deleteGameCopy(title, userAccountId);
    }
}
