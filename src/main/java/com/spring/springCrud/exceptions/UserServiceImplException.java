package com.spring.springCrud.exceptions;

public class UserServiceImplException extends Exception{
    public UserServiceImplException (){
        super("Something went wrong. Please try again later!");
    }
}
