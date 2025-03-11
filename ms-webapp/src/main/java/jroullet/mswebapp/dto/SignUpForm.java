package jroullet.mswebapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpForm {

    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
//    private String role;

}
