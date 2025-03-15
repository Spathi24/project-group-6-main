package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        reviewRepo.deleteAll();
        userAccountRepo.deleteAll();
        ;
        gameRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadReview() {

        String name = "abc";
        String password = "abcde";
        String email = "abc@mail.mcgill.ca";
        AccountType accountType = AccountType.PLAYER;
        UserAccount abc = new UserAccount(name, password, email, accountType);
        abc = userAccountRepo.save(abc);

        String title = "Monopoly";
        String description = "Be richer";
        String category = "Roll and Move";
        Game monopoly = new Game(title, description, category);
        monopoly = gameRepo.save(monopoly);

        int rating = 9;
        String comment = "Good";
        Date date = Date.valueOf("2025-01-17");
        Review review = new Review(new Review.ReviewKey(abc, monopoly), rating, comment, date);
        review = reviewRepo.save(review);

        Review reviewFromDb = reviewRepo.findReviewByReviewKey(review.getReviewKey());

        assertNotNull(reviewFromDb);
        assertNotNull(reviewFromDb.getReviewKey());
        assertNotNull(reviewFromDb.getReviewKey().getReviewer());
        assertEquals(abc.getUserAccountID(), reviewFromDb.getReviewKey().getReviewer().getUserAccountID());
        assertNotNull(reviewFromDb.getReviewKey().getGameToReview());
        assertEquals(monopoly.getTitle(), reviewFromDb.getReviewKey().getGameToReview().getTitle());

    }
}
