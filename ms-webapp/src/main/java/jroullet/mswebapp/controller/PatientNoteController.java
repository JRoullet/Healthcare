package jroullet.mswebapp.controller;

import jroullet.mswebapp.clients.NotesFeignClient;
import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.dto.NoteDto;
import jroullet.mswebapp.dto.PatientId;
import jroullet.mswebapp.model.Note;
import jroullet.mswebapp.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/patient")
public class PatientNoteController {

    @Autowired
    NotesFeignClient notesFeignClient;
    @Autowired
    private PatientFeignClient patientFeignClient;
//    @Autowired
//    private NoteService noteService;

    private final static Logger logger = LoggerFactory.getLogger(PatientNoteController.class);

    // Show List of notes
    @GetMapping("/{id}/notes")
    public String getNotesByPatientId(@PathVariable Long id, Model model) {
        List<Note> notes = notesFeignClient.getNotesByPatientId(id);
        PatientId patientId = new PatientId();
        patientId.setId(id);
        Patient patient = patientFeignClient.getPatientById(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("patientId", id);

        // Refers to update note's pop up
        model.addAttribute("noteToUpdate", new Note());
        return "patient-notes";
    }

    // Add Note
    @GetMapping("/{id}/notes/add")
    public String showAddNoteToPatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patientId", id);
        model.addAttribute("noteDto", new NoteDto());
        return "add-note-to-patient";
    }

    // Add Note
    @PostMapping(value = "/{id}/notes/add", consumes = "application/json")
    public String addNoteToPatient(@PathVariable Long id, @ModelAttribute NoteDto noteDto) {
        noteDto.setPatientId(id);
        noteDto.setId(null);
        Note note = notesFeignClient.createNote(noteDto).getBody();
        if(note != null) {
            logger.info("Creating note with id {}", note.getId());
        }

        return "redirect:/patient/" + id + "/notes";
    }

    // Update Note form display
    @GetMapping ("/{id}/notes/update/{noteId}")
    public String showUpdateNoteToPatientForm(@PathVariable("id") Long id, @PathVariable("noteId") String noteId, Model model) {
        System.out.println(">> [DEBUG] GET /notes/update called with noteId = " + noteId);
        PatientId patientId = new PatientId();
        // Convert Long id to patientId
        patientId.setId(id);
        Patient patient = patientFeignClient.getPatientById(patientId);
        if(patient == null){
            throw new RuntimeException("Patient not found with ID : " + id);
        }

        List<Note> notes = notesFeignClient.getNotesByPatientId(id);
        Note note = notesFeignClient.getNoteById(noteId).getBody();

        model.addAttribute("patient", patient);
        model.addAttribute("patientId", id);
        model.addAttribute("notes", notes);
        model.addAttribute("noteToUpdate", note);
        model.addAttribute("openModal", true);// open modale and popUp
        return "patient-notes";
    }

    // Update Note form submit
    @PostMapping(value = "/{id}/notes/update/{noteId}")
    public String updateNoteToPatient(@PathVariable("id") Long id,
                                      @PathVariable("noteId") String noteId,
                                      @ModelAttribute("noteToUpdate") Note note,
                                      Model model){
        try{
            note.setId(noteId);
            note.setPatientId(id);

            if (note.getCreationDate() == null) {
                ResponseEntity<Note> existingNoteResponse = notesFeignClient.getNoteById(noteId);
                Note existingNote = existingNoteResponse.getBody();
                if (existingNote != null) {
                    note.setCreationDate(existingNote.getCreationDate());
                }
            }
            notesFeignClient.updateNote(noteId, note);
            return "redirect:/patient/" + id + "/notes";
        } catch (Exception e){
            model.addAttribute("error", "Error updating note: " + e.getMessage());

            // In case of an error calls back the method's name + its attributes                (instead of the template)
            return getNotesByPatientId(id, model);
        }
    }

    @GetMapping("/{id}/notes/delete/{noteId}")
    public String deleteNote(@PathVariable("id") Long id, @PathVariable("noteId") String noteId) {
        notesFeignClient.deleteNote(noteId);
        return "redirect:/patient/" + id + "/notes";
    }

//    // Pop UP endpoint on notes list
//    @GetMapping("/{id}/notes/get/{noteId}")
//    @ResponseBody
//    public Note getNoteForEdit(@PathVariable("id") Long id,
//                               @PathVariable("noteId") String noteId) {
//
//        ResponseEntity<Note> response = notesFeignClient.getNoteById(noteId);
//        if(response.getBody() != null){
//            Note note = response.getBody();
//            logger.info("Note ID : {}", note.getId());
//            logger.info("Note content : {}", note.getContent());
//            logger.info("Note date : {}", note.getCreationDate());
//        return note;
//        }else{
//           throw new RuntimeException("Note not found with ID : " + noteId);
//        }
//    }


}
