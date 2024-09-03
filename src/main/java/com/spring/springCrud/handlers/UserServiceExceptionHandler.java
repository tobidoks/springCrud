package com.spring.springCrud.handlers;

import com.spring.springCrud.dtos.ApiResponseDto;
import com.spring.springCrud.dtos.ApiResponseStatusDto;
import com.spring.springCrud.exceptions.UserAlreadyExistsException;
import com.spring.springCrud.exceptions.UserNotFoundException;
import com.spring.springCrud.exceptions.UserServiceImplException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(ApiResponseStatusDto.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDto<>(ApiResponseStatusDto.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserServiceImplException.class)
    public ResponseEntity<ApiResponseDto<?>> UserServiceLogicExceptionHandler(UserServiceImplException exception) {
        return ResponseEntity.badRequest().body(new ApiResponseDto<>(ApiResponseStatusDto.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new ApiResponseDto<>(ApiResponseStatusDto.FAIL.name(), errorMessage.toString()));
    }
}
