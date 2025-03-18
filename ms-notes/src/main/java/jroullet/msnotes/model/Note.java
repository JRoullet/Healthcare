package jroullet.msnotes.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "notes")
public class Note {
    @Id
    private ObjectId id;
    private LocalDate creationDate;
    private String content;
    private Long patientId;
}
