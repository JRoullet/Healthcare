package jroullet.msdiabete.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Note {
    private String id;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private String content;
    private Long patientId;
}
