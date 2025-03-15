package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review,Review.ReviewKey> {
    public Review findReviewByReviewKey(Review.ReviewKey reviewKey);
}
