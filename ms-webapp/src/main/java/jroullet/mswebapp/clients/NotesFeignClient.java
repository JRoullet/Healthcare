package jroullet.mswebapp.clients;

import jroullet.mswebapp.dto.NoteDto;
import jroullet.mswebapp.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="ms-notes")

public interface NotesFeignClient {

    // Read all notes for a patient
    @GetMapping("/notes/patient/{patientId}/all")
    List<Note> getNotesByPatientId(@PathVariable Long patientId);

    //Read one note
    @GetMapping("/notes/get/{id}")
    ResponseEntity<Note> getNoteById(@PathVariable String id);


//    [HTML Formulaire] (Thymeleaf)
//            ⬇️ POST in x-www-form-urlencoded
//[Spring WebApp Controller] (@ModelAttribute)
//            ⬇️ Feign → POST JSON
//[Microservice Notes] (@RequestBody, consumes = application/json)

    // Add note -- between microservices, annotation consumes = "application/json" is essential, otherwise server expects x-www-form-urlencoded
    @PostMapping(value = "/notes/add", consumes = "application/json")
    ResponseEntity<Note> createNote(@RequestBody NoteDto noteDto);

    //Update note
    @PostMapping(value = "/notes/update/{id}", consumes = "application/json")
    ResponseEntity<Note> updateNote(@PathVariable String id,
                                    @RequestBody Note note);


    //Delete note
    @DeleteMapping("/notes/delete/{id}")
    ResponseEntity<String> deleteNote(@PathVariable String id);


//    // Add note by patient id
//    @PostMapping("/{patientId}/add")
//    NoteDto addNoteToPatient(@PathVariable Long patientId, @RequestBody NoteDto noteDto);

//    // Update Note
//    @PutMapping("/update/{noteId}")
//    NoteDto updateNoteToPatient(@PathVariable Long patientId, @PathVariable String noteId, @RequestBody NoteDto noteDto);



    // A implementer, car pour le moment, on va chercher les informations en cascade depuis webapp vers patient puis vers notes
//    @GetMapping("/api/notes/patient/{patientId}")
//    List<NoteDto> getNotesByPatientId(@PathVariable Long patientId);
//
//    @PostMapping("/api/create-note")
//    NoteDto createNote(@RequestBody NoteDto noteDto);
//
//    @PutMapping("/api/{patientId}/")
//    NoteDto updateNote(@RequestBody NoteDto noteDto);
}
