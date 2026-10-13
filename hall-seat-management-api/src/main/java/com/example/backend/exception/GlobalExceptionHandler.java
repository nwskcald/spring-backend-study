package com.example.backend.exception;

import java.util.stream.Collectors;
import com.example.backend.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ErrorResponse> seatNotFoundError(
        SeatNotFoundException e
    ){
        ErrorResponse errorResponse =
            new ErrorResponse("SEAT_NOT_FOUND", e.getMessage());

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(DuplicateSeatException.class)
    public ResponseEntity<ErrorResponse> duplicateSeatError(
        DuplicateSeatException e
    ){
        ErrorResponse errorResponse =
            new ErrorResponse("DUPLICATE_SEAT", e.getMessage());

        return ResponseEntity.status(409).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationError(
        MethodArgumentNotValidException e
    ){
        String message = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.joining(" / "));

        ErrorResponse errorResponse =
            new ErrorResponse("VALIDATION_ERROR", message);

        return ResponseEntity.status(400).body(errorResponse);
    }
}