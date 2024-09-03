package com.spring.springCrud.dtos;

import com.spring.springCrud.models.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "Username is required!")
    @Size(min= 3, message = "Username must have at least 3 characters!")
    @Size(max= 20, message = "Username can have have at most 20 characters!")
    private String userName;

    @Email(message = "Email is not in valid format!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message= "Gender is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
