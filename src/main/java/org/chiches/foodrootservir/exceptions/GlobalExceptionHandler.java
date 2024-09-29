package org.chiches.foodrootservir.exceptions;

import org.chiches.foodrootservir.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionResponse> catchDatabaseException(DatabaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
        return responseEntity;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> catchResourceNotFoundException(ResourceNotFoundException e) {
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        return responseEntity;
    }
}
