package jroullet.mswebapp.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class Note {
    @Id
    private String id;
    private LocalDate creationDate;
    private String content;
    private Long patientId;
}
