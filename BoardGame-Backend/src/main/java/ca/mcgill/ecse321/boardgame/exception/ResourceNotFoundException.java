package ca.mcgill.ecse321.boardgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class ResourceNotFoundException extends RuntimeException {
    private HttpStatus status;

    public ResourceNotFoundException(@NonNull HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
