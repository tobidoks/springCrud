package com.spring.springCrud.controllers;

import com.spring.springCrud.dtos.ApiResponseDto;
import com.spring.springCrud.dtos.UserRegistrationDto;
import com.spring.springCrud.exceptions.UserAlreadyExistsException;
import com.spring.springCrud.exceptions.UserNotFoundException;
import com.spring.springCrud.exceptions.UserServiceImplException;
import com.spring.springCrud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("api/vi/users")
@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto)
        throws UserAlreadyExistsException, UserServiceImplException {
        return userService.registerUser(userRegistrationDto);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<ApiResponseDto<?>>  updateUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto, @PathVariable int id)
        throws UserNotFoundException, UserServiceImplException{
        return userService.updateUser(userRegistrationDto, id);
    }

    @GetMapping ("/get/all")
    public ResponseEntity<ApiResponseDto<?>>  getAllUsers()
        throws UserServiceImplException{
        return userService.getAllUsers();
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<ApiResponseDto<?>> deleteUser(@PathVariable int id)
        throws UserNotFoundException, UserServiceImplException{
        return userService.deleteUser(id);
    }


}
