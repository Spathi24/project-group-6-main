package ca.mcgill.ecse321.boardgame.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.*;
import java.util.Objects;

@Entity(name = "Review")
public class Review implements Serializable {

    @EmbeddedId
    private ReviewKey reviewKey;

    private int rating;

    private String comment;

    @Temporal(TemporalType.DATE)
    private Date date;

    protected Review() {
    }

    public Review(ReviewKey reviewKey, int rating, String comment, Date date) {
        this.reviewKey = reviewKey;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public ReviewKey getReviewKey() {
        return reviewKey;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    @Embeddable
    public static class ReviewKey implements Serializable {

        @ManyToOne
        private UserAccount reviewer;

        @ManyToOne
        private Game gameToReview;

        public ReviewKey() {
        }

        public ReviewKey(UserAccount reviewer, Game gameToReview) {
            this.reviewer = reviewer;
            this.gameToReview = gameToReview;
        }

        public UserAccount getReviewer() {
            return reviewer;
        }

        public Game getGameToReview() {
            return gameToReview;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ReviewKey)) {
                return false;
            }
            ReviewKey that = (ReviewKey) obj;
            return Objects.equals(this.reviewer.getUserAccountID(), that.reviewer.getUserAccountID())
                    && this.gameToReview.getTitle() == that.gameToReview.getTitle();
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.reviewer.getUserAccountID(), this.gameToReview.getTitle());
        }
    }

}
