package jroullet.msnotes.controller;

import jroullet.msnotes.dto.NoteId;
import jroullet.msnotes.dto.PatientId;
import jroullet.msnotes.model.Note;
import jroullet.msnotes.service.NoteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class NoteController {

    private NoteService noteService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);


    @PostMapping("/create-note")
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @PostMapping("/get-all-notes")
    public List<Note> getAllNotes(@RequestBody PatientId patientId) {
        return noteService.findNotesByPatientId(patientId);
    }

    @PostMapping("/note")
    public Note getNoteById(@RequestBody NoteId noteId) {
        logger.info("Get note by id {}", noteId);
        return noteService.findNoteById(noteId);
    }

    @PutMapping("/update")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        noteService.updateNoteById(note);
        if (noteService.updateNoteById(note)) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteNoteById(@RequestBody NoteId noteId) {
        noteService.deleteNoteById(noteId);
        return new ResponseEntity<>("Note deleted", HttpStatus.OK);
    }
}
