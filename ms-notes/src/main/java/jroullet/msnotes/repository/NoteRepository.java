package jroullet.msnotes.repository;

import jroullet.msnotes.dto.PatientId;
import jroullet.msnotes.model.Note;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    
    List<Note> findNotesByPatientId(Long patientId);

    Optional<Note> findNoteById(String id);

    String id(String id);
}
