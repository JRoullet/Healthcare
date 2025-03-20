package jroullet.mswebapp.controller;

import jroullet.mswebapp.clients.NotesFeignClient;
import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.dto.NoteDto;
import jroullet.mswebapp.dto.PatientId;
import jroullet.mswebapp.model.Note;
import jroullet.mswebapp.model.Patient;
import jroullet.mswebapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientNoteController {

    @Autowired
    NotesFeignClient notesFeignClient;
    @Autowired
    private PatientFeignClient patientFeignClient;
    @Autowired
    private NoteService noteService;

    // Show List of notes
    @GetMapping("/{id}/notes")
    public String getNotesByPatientId(@PathVariable Long id, Model model) {
        List<Note> notes = notesFeignClient.getNotesByPatientId(id);
        model.addAttribute("notes", notes);
        model.addAttribute("patientId", id);
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
    @PostMapping("/{id}/notes/add")
    public String addNoteToPatient(@PathVariable Long id, @ModelAttribute Note note) {
        notesFeignClient.createNote(note);
        return "redirect:/patient/" + id + "/notes";
    }

    // Update Note form display
    @GetMapping ("/{id}/notes/update/{noteId}")
    public String showUpdateNoteToPatientForm(@PathVariable("id") Long id, @PathVariable("noteId") String noteId, Model model) {
        PatientId patientId = new PatientId();
        // Convert Long id to patientId
        patientId.setId(id);
        Patient patient = patientFeignClient.getPatientById(patientId);
        if(patient == null){
            throw new RuntimeException("Patient not found with ID : " + id);
        }
        Note noteToUpdate = noteService.getNoteById(id, noteId);

        model.addAttribute("patient", patient);
        model.addAttribute("noteToUpdate", noteToUpdate);
        return "update-note-to-patient";
    }

    // Update Note form submit
    @PostMapping("/{id}/notes/update/{noteId}")
    public String updateNoteToPatient(@PathVariable("id") Long id, @PathVariable("noteId") String noteId, @ModelAttribute("noteToUpdate") Note note, Model model){
        try{
            Note updateNote = notesFeignClient.updateNote(note).getBody();
            return "redirect:/patient/" + id + "/notes";
        } catch (Exception e){
            model.addAttribute("error", "Error updating note" + e.getMessage());
        }
        return "redirect:/patient/" + id + "/notes";
    }



}
