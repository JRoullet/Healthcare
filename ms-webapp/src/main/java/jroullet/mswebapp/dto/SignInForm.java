package jroullet.mswebapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class SignInForm {
    @NotBlank (message = "Email needed")
    @Email (message = "Invalid email")
    private String username;
    @NotBlank (message = "Password needed")
    private String password;

}
