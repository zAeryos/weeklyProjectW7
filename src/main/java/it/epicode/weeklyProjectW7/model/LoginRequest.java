package it.epicode.weeklyProjectW7.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
