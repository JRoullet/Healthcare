package jroullet.mswebapp.controller;

import jroullet.mswebapp.clients.DiabeteFeignClient;
import jroullet.mswebapp.clients.NotesFeignClient;
import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.dto.NoteDto;
import jroullet.mswebapp.model.Note;
import jroullet.mswebapp.model.Patient;
import jroullet.mswebapp.model.RiskLevel;
import jroullet.mswebapp.utility.RiskLevelStyleUtil;
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
    @Autowired
    private DiabeteFeignClient diabeteFeignClient;

    private final static Logger logger = LoggerFactory.getLogger(PatientNoteController.class);


    // Show List of notes
    @GetMapping("/{id}/notes")
    public String getNotesByPatientId(@PathVariable Long id, Model model) {
        List<Note> notes = notesFeignClient.getNotesByPatientId(id);
        Patient patient = patientFeignClient.getPatientById(id);
        RiskLevel riskLevel;
        try {
            riskLevel = diabeteFeignClient.determineRiskLevel(id);
        } catch (Exception e) {
            logger.warn("Unable to retrieve risk level for patient {}: {}", id, e.getMessage());
            riskLevel = RiskLevel.NONE; // default fallBack
        }

        // Mapping a css style to risk level
        String cssClass = RiskLevelStyleUtil.getCssClass(riskLevel);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("patientId", id);
        // Risk displaying
        model.addAttribute("riskLevel", riskLevel.getLabel());
        // Mapping css class
        model.addAttribute("riskClass", cssClass);

        // Refers to update note's pop up
        model.addAttribute("noteToUpdate", new Note());
        //patient-notes objects have to all be filled (fields are related to Pop-up => noteToUpdate)
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
    @PostMapping(value = "/{id}/notes/add")
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
        Patient patient = patientFeignClient.getPatientById(id);
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



}
