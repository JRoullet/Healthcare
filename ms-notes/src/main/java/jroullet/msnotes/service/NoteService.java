package jroullet.msnotes.service;

import jroullet.msnotes.dto.NoteId;
import jroullet.msnotes.dto.PatientId;
import jroullet.msnotes.model.Note;
import jroullet.msnotes.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    public Note createNote(Note note) {
        note.setCreationDate(LocalDate.now());
        return noteRepository.save(note);
    }

    public List<Note> findNotesByPatientId(Long patientId) {
        return noteRepository.findNotesByPatientId(patientId);
    }

    public Note findNoteById(String id) {
        Optional<Note> note = noteRepository.findNoteById(id);
        return note.orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }

    public Boolean updateNoteById(Note updatedNote) {
        if(updatedNote == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }
        if(updatedNote.getId() == null) {
            throw new IllegalArgumentException("Note id cannot be null");
        }
        try{
            Optional<Note> existingNoteOpt = noteRepository.findNoteById(updatedNote.getId());
            if(existingNoteOpt.isPresent()) {
                Note existingNote = existingNoteOpt.get();
                // Avoid code repetition and copy each property of updated patient to existing patient, except id (ignored)
                BeanUtils.copyProperties(updatedNote, existingNote, "id");
                existingNote.setLastUpdateDate(LocalDate.now());
                noteRepository.save(existingNote);
                logger.info("Note Updated");
                return true;
            }
            return false;
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Error updating note " + updatedNote.getId() + " in database", e);
        }
        catch (IllegalArgumentException e){
            throw new RuntimeException("Error copying note properties " + updatedNote.getId(), e);
        }
    }

    public void deleteNoteById(String id) {
        Note note = noteRepository.findNoteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
        noteRepository.delete(note);
        logger.info("Note Deleted");
    }
}
