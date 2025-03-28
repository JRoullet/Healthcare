package jroullet.mspatient.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPatchDto {
    private Long id;
    private String username;
    private String password;
    private String role;
}
