package ca.mcgill.ecse321.boardgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class BoardGameException extends RuntimeException {
    private HttpStatus status;

    public BoardGameException(@NonNull HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
