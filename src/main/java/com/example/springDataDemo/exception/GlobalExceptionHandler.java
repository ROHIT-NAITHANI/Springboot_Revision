package com.example.springDataDemo.exception;

import com.example.springDataDemo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException exception){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(
               "USER_NOT_FOUND", exception.getMessage()

        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        HashMap<String,String> fieldErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(Error -> {
            fieldErrors.put(Error.getField(),Error.getDefaultMessage());
        });

        StringBuilder errorMsg = new StringBuilder();
        boolean isFirst = true;
        for (String field : fieldErrors.keySet()){
            if (!isFirst){
                errorMsg.append(" ,");
            }
            isFirst = false;
            errorMsg.append(field).append(" : ").append(fieldErrors.get(field));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(
                "INVALID_INPUT", errorMsg.toString()

        ));
    }
}
