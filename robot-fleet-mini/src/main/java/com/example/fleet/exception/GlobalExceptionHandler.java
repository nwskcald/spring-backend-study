package com.example.fleet.exception;

import com.example.fleet.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(DuplicateRobotException.class)
    public ResponseEntity<ErrorResponse> duplicateRobotError(
        DuplicateRobotException e
    ){

        ErrorResponse response = new ErrorResponse(
            "ROBOT_ALREADY_EXISTS", e.getMessage()
        );

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(DuplicateTaskException.class)
    public ResponseEntity<ErrorResponse> duplicateTaskError(
        DuplicateTaskException e
    ){

        ErrorResponse response = new ErrorResponse(
            "TASK_ALREADY_EXISTS", e.getMessage()
        );

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(RobotAlreadyAssignedException.class)
    public ResponseEntity<ErrorResponse> robotAlreadyAssignedError(
        RobotAlreadyAssignedException e
    ){

        ErrorResponse response = new ErrorResponse(
            "ROBOT_BUSY", e.getMessage()
        );

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(TaskAlreadyAssignedException.class)
    public ResponseEntity<ErrorResponse> taskAlreadyAssignedError(
        TaskAlreadyAssignedException e
    ){

        ErrorResponse response = new ErrorResponse(
            "TASK_ALREADY_ASSIGNED", e.getMessage()
        );

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(RobotNotFoundException.class)
    public ResponseEntity<ErrorResponse> robotNotFoundError(
        RobotNotFoundException e
    ){

        ErrorResponse response = new ErrorResponse(
            "ROBOT_NOT_FOUND", e.getMessage()
        );

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> taskNotFoundError(
        TaskNotFoundException e
    ){

        ErrorResponse response = new ErrorResponse(
            "TASK_NOT_FOUND", e.getMessage()
        );

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
        MethodArgumentNotValidException e
    ){
        ErrorResponse response = 
            new ErrorResponse("INVALID_REQUEST", "필수 입력값이 누락되거나 올바르지 않습니다.");

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
        Exception e
    ){
        ErrorResponse response = 
            new ErrorResponse("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");
        
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(RobotNotAssignedException.class)
    public ResponseEntity<ErrorResponse> robotNotAssigned(
        RobotNotAssignedException e
    ) {
        return ResponseEntity.status(409)
            .body(new ErrorResponse(
                "ROBOT_NOT_ASSIGNED",
                e.getMessage()
            ));
    }

    @ExceptionHandler(TaskNotAssignedException.class)
    public ResponseEntity<ErrorResponse> taskNotAssigned(
        TaskNotAssignedException e
    ) {
        return ResponseEntity.status(409)
            .body(new ErrorResponse(
                "TASK_NOT_ASSIGNED",
                e.getMessage()
            ));
    }
}