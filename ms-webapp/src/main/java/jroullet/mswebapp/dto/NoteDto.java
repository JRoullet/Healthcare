package jroullet.mswebapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoteDto {
    private String id;
    private LocalDate creationDate;
    private String content;
    private Long patientId;
}
