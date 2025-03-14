package jroullet.mswebapp.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String gender;
    private String address;
    private String phone;
    private String email;
}
