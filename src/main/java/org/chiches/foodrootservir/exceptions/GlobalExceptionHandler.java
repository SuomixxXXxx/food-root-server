package org.chiches.foodrootservir.exceptions;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.chiches.foodrootservir.dto.ExceptionResponse;
import org.chiches.foodrootservir.exceptions.file.EmptyFileException;
import org.chiches.foodrootservir.exceptions.file.InvalidFileFormatException;
import org.chiches.foodrootservir.exceptions.order.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<ExceptionResponse> catchResourceNotFoundException(ResourceNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
        return responseEntity;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder strBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(String.format("%s: %s\n", fieldName, message));
        });
        ExceptionResponse exceptionResponse = new ExceptionResponse(strBuilder.toString());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
        return responseEntity;
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ExceptionResponse> catchOrderException(OrderException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
        return responseEntity;
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ExceptionResponse> catchInvalidArgumentException(InvalidArgumentException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
        return responseEntity;
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ExceptionResponse> catchEmptyFileException(EmptyFileException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
        return responseEntity;
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ExceptionResponse> catchInvalidFileFormatException(InvalidFileFormatException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        ResponseEntity<ExceptionResponse> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
        return responseEntity;
    }

}
