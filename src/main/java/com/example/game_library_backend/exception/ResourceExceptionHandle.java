package com.example.game_library_backend.exception;


import com.example.game_library_backend.exception.customized.BadRequestException;
import com.example.game_library_backend.exception.customized.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.example.game_library_backend.utils.DateUtils.getCurrentTime;

@ControllerAdvice
public class ResourceExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation errors", getCurrentTime());
        for(FieldError x : e.getBindingResult().getFieldErrors()){
            error.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), getCurrentTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException e, HttpServletRequest request) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), getCurrentTime());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}