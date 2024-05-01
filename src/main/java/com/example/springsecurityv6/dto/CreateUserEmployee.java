package com.example.springsecurityv6.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserEmployee {
    @NotBlank
    private String fullName;
    private int age ;
    @NotBlank
    private String email ;
    @NotBlank
    private String password ;
    @NotBlank
    private String confirmPassword ;
    @NotBlank
    private String role ;
    private boolean account_state ;
}
