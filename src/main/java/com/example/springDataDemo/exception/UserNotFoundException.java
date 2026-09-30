package com.example.springDataDemo.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String errorMsg){
        super(errorMsg);

    }


}
