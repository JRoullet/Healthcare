package jroullet.msnotes.controller;

import jroullet.msnotes.model.Note;
import jroullet.msnotes.service.NoteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    // ADD
    @PostMapping("/add")
    public ResponseEntity<Note> createNote(@RequestBody Note note){
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
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        note.setId(id);
        boolean isUpdated = noteService.updateNoteById(note);
        if (isUpdated) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
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

//    // MUST BE IN MS-WEBAPP
//    @PutMapping("/patient/{patientId}/update/{noteId}")
//    public ResponseEntity<Note> updateNote(@PathVariable Long patientId, @PathVariable String noteId, @RequestBody NoteDto noteDto) {
//        Note updatedNote = noteService.updateNote(patientId, noteId, noteDto);
//        return ResponseEntity.ok(updatedNote);
//    }

    // Cascade callings - no use
    //    // MS-Patient CALLING (Converting Object Long from MS-Patient to PatientId in MS-Notes (Here))
//    @GetMapping("/patient/{patientId}/notes")
//    public ResponseEntity<List<NoteDto>> getNotesByPatientId(@PathVariable Long patientId) {
//        // Creating PatientId Type object for findNotesByPatientId Method and setting it
//        PatientId patientIdObj = new PatientId();
//        patientIdObj.setPatientId(patientId);
//        // Pass it to the method
//        List<Note> notes = noteService.findNotesByPatientId(patientIdObj);
//        List<NoteDto> noteDtos = notes.stream().map(noteMapper::toDTO).toList();
//        // Return notes
//        return ResponseEntity.ok(noteDtos);
//    }
    //    // MS-Patient CALLING (DTO use)
//    @PostMapping("/create-note")
//    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto){
//        // Convert to Note to use "createNote" from noteService
//        Note note = noteMapper.toEntity(noteDto);
//        Note createdNote = noteService.createNote(note);
//        //Convert Back to NoteDto type to send to MS-Patient
//        NoteDto createdNoteDTO= noteMapper.toDTO(createdNote);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdNoteDTO);
//    }


}
