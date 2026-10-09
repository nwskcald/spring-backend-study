package com.example.backend.exception;

import com.example.backend.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}