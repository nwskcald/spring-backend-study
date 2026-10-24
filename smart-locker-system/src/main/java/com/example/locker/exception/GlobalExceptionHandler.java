package com.example.locker.exception;

import com.example.locker.dto.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ActiveStorageNotFoundException.class)
    public ResponseEntity<ErrorResponse> activeStorageNotFoundError(
        ActiveStorageNotFoundException e
    ){
        ErrorResponse response = 
            new ErrorResponse("ACTIVE_STORAGE_NOT_FOUND", e.getMessage());
        
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(AlreadyOccupiedLockerException.class)
    public ResponseEntity<ErrorResponse> alreadyOccupiedLockerError(
        AlreadyOccupiedLockerException e
    ){
        ErrorResponse response = 
            new ErrorResponse("LOCKER_OCCUPIED", e.getMessage());
        
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(DuplicateLockerException.class)
    public ResponseEntity<ErrorResponse> duplicateLockerError(
        DuplicateLockerException e
    ){
        ErrorResponse response = 
            new ErrorResponse("DUPLICATE_LOCKER", e.getMessage());
        
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(EndCompletedStorageException.class)
    public ResponseEntity<ErrorResponse> endCompletedStorageError(
        EndCompletedStorageException e
    ){
        ErrorResponse response = 
            new ErrorResponse("END_COMPLETED_STORAGE", e.getMessage());
        
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(LockerNotFoundException.class)
    public ResponseEntity<ErrorResponse> lockerNotFoundError(
        LockerNotFoundException e
    ){
        ErrorResponse response = 
            new ErrorResponse("LOCKER_NOT_FOUND", e.getMessage());
        
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(StorageNotFoundException.class)
    public ResponseEntity<ErrorResponse> storageNotFoundError(
        StorageNotFoundException e
    ){
        ErrorResponse response = 
            new ErrorResponse("STORAGE_NOT_FOUND", e.getMessage());
        
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(TerminateAvailableLockerException.class)
    public ResponseEntity<ErrorResponse> terminateAvailableLockerError(
        TerminateAvailableLockerException e
    ){
        ErrorResponse response = 
            new ErrorResponse("TERMINATE_AVAILABLE_LOCKER", e.getMessage());
        
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableError(
        HttpMessageNotReadableException e
    ){
        ErrorResponse response = 
            new ErrorResponse("HTTP_MESSAGE_NOT_READABLE", e.getMessage());
        
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidError(
        MethodArgumentNotValidException e
    ){
        ErrorResponse response = 
            new ErrorResponse("METHOD_ARGUMENT_NOT_VALID", e.getMessage());
        
        return ResponseEntity.status(400).body(response);
    }
}