package jroullet.msnotes.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private String content;
    private Long patientId;
}
