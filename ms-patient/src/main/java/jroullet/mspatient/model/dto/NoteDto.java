package jroullet.mspatient.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NoteDto {
    private String id;
    private LocalDate creationDate;
    private String content;
    private Long patientId;
}
