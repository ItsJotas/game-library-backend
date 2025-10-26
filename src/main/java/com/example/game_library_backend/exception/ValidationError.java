package com.example.game_library_backend.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String message, String timestamp){
        super(status, message, timestamp);
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }

    public List<FieldMessage> getErrors(){
        return errors;
    }
}