package com.spring.springCrud.services;

import com.spring.springCrud.dtos.ApiResponseDto;
import com.spring.springCrud.dtos.UserRegistrationDto;
import com.spring.springCrud.exceptions.UserAlreadyExistsException;
import com.spring.springCrud.exceptions.UserNotFoundException;
import com.spring.springCrud.exceptions.UserServiceImplException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<ApiResponseDto<?>> registerUser(UserRegistrationDto newUserDetails)
            throws UserAlreadyExistsException, UserServiceImplException;

    ResponseEntity<ApiResponseDto<?>> updateUser(UserRegistrationDto newUserDetails, int id)
            throws UserNotFoundException, UserServiceImplException;

    ResponseEntity<ApiResponseDto<?>> deleteUser(int id)
            throws UserNotFoundException, UserServiceImplException;

    ResponseEntity<ApiResponseDto<?>> getAllUsers()
            throws UserServiceImplException;

}
