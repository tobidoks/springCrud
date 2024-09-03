package com.spring.springCrud.services;

import com.spring.springCrud.dtos.ApiResponseDto;
import com.spring.springCrud.dtos.ApiResponseStatusDto;
import com.spring.springCrud.dtos.UserRegistrationDto;
import com.spring.springCrud.exceptions.UserAlreadyExistsException;
import com.spring.springCrud.exceptions.UserNotFoundException;
import com.spring.springCrud.exceptions.UserServiceImplException;
import com.spring.springCrud.models.User;
import com.spring.springCrud.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> registerUser(UserRegistrationDto newUserDetails)
            throws UserAlreadyExistsException, UserServiceImplException {

        try {
            if (userRepository.findByEmail(newUserDetails.getEmail()) != null){
                throw new UserAlreadyExistsException("Registration failed: User already exists with email " + newUserDetails.getEmail());
            }
            if (userRepository.findByUsername(newUserDetails.getUserName()) != null){
                throw new UserAlreadyExistsException("Registration failed: User already exists with username " + newUserDetails.getUserName());
            }

            User newUser = new User(
                    newUserDetails.getUserName(), newUserDetails.getEmail(), newUserDetails.getGender(), LocalDateTime.now()
            );

            userRepository.save(newUser);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponseDto<>(ApiResponseStatusDto.SUCCESS.name(), "New user account has been successfully created!"));

        }catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
            log.error("Failed to create new user account: " + e.getMessage());
            throw new UserServiceImplException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllUsers() throws UserServiceImplException {
        try {
            List<User> users = userRepository.findAllByOrderByRegDateAndTimeDesc();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatusDto.SUCCESS.name(), users)
                    );

        }catch (Exception e) {
            log.error("Failed to fetch all users: " + e.getMessage());
            throw new UserServiceImplException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> updateUser(UserRegistrationDto newUserDetails, int id)
            throws UserNotFoundException, UserServiceImplException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

            user.setEmail(newUserDetails.getEmail());
            user.setUsername(newUserDetails.getUserName());
            user.setGender(newUserDetails.getGender());

            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatusDto.SUCCESS.name(), "User account updated successfully!")
                    );

        }catch(UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }catch(Exception e) {
            log.error("Failed to update user account: " + e.getMessage());
            throw new UserServiceImplException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> deleteUser(int id) throws UserServiceImplException, UserNotFoundException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

            userRepository.delete(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatusDto.SUCCESS.name(), "User account deleted successfully!")
                    );
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to delete user account: " + e.getMessage());
            throw new UserServiceImplException();
        }
    }
}