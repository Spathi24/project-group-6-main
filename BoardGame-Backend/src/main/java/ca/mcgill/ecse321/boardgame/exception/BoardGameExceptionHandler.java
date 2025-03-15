package ca.mcgill.ecse321.boardgame.exception;

import ca.mcgill.ecse321.boardgame.dto.ErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BoardGameExceptionHandler {
    @ExceptionHandler(BoardGameException.class)
    public ResponseEntity<ErrorDto> handleEventRegistrationException(BoardGameException e) {
        return new ResponseEntity<ErrorDto>(new ErrorDto(e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            errors.add(cv.getMessage());
        }
        return new ResponseEntity<ErrorDto>(new ErrorDto(errors), HttpStatus.BAD_REQUEST);
    }
}
