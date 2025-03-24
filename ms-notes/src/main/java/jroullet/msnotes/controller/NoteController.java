package jroullet.msnotes.controller;

import jroullet.msnotes.dto.NoteDto;
import jroullet.msnotes.mapper.NoteMapper;
import jroullet.msnotes.model.Note;
import jroullet.msnotes.service.NoteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private NoteMapper noteMapper;
    private NoteService noteService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    // ADD
    @PostMapping("/add")
    public ResponseEntity<Note> createNote(@RequestBody NoteDto noteDto){
        noteDto.setId(null);
        Note note = noteMapper.toEntity(noteDto);
        Note createdNote = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    // GET
    @GetMapping("/get/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        logger.info("Get note by id {}", id);
        Note note = noteService.findNoteById(id);
        if(note == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

    // UPDATE
    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        note.setId(id);
        boolean isUpdated = noteService.updateNoteById(note);
        if (isUpdated) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>("Note deleted", HttpStatus.OK);
    }

    // GET ALL
    @GetMapping("/patient/{patientId}/all")
    public ResponseEntity<List<Note>> getAllNotes(@PathVariable Long patientId) {
        List<Note> notes = noteService.findNotesByPatientId(patientId);
        if (notes == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}
