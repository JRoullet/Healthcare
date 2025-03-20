package jroullet.mspatient.controller;

import jroullet.mspatient.model.Patient;
import jroullet.mspatient.model.dto.NoteDto;
import jroullet.mspatient.model.dto.PatientId;
import jroullet.mspatient.service.PatientService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private final Logger logger = LoggerFactory.getLogger(PatientController.class);

    // Create Patient
    @PostMapping("/new")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        logger.info("created patient");
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    // Read Patient (PostMapping PatientId DTO)
    @PostMapping("/get")
    public ResponseEntity<?> getPatientById(@RequestBody PatientId patientId) {
        Optional<Patient> patient = patientService.findPatientById(patientId);
        if(patient.isPresent()) {
            logger.info("Patient {} found", patientId.getId());
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient with id " + patientId.getId() + " not found");
    }

    // Update Patient
    @PutMapping("/update")
    public ResponseEntity<Patient> updatePatientById(@RequestBody Patient updatedPatient) {
        patientService.updatePatient(updatedPatient);
        if (patientService.updatePatient(updatedPatient)) {
            logger.info("updated patient");
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        }
        logger.info("patient could not be updated");
        return new ResponseEntity<>(updatedPatient, HttpStatus.NOT_FOUND);
    }

    // Read List of Patients
    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.findAll();
        if (patients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

//    // Create Note
//    @PostMapping("/{id}/notes")
//    public ResponseEntity<NoteDto> addNoteToPatient(@PathVariable Long id, @RequestBody NoteDto noteDto){
//        NoteDto createdNote = patientService.addNoteToPatient(id, noteDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
//    }
//
//    // Read List of Notes by patient
//    @GetMapping("/{id}/notes")
//    public ResponseEntity<List<NoteDto>> getNotesByPatientId(@PathVariable Long id){
//        List<NoteDto> notes = patientService.getNotesByPatientId(id);
//        return new ResponseEntity<>(notes, HttpStatus.OK);
//    }
//
//    // Read Patient (GetMapping, Long id from Patient)
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getPatientById(@PathVariable Long id) {
//        Optional<Patient> patient = patientService.getPatientById(id);
//        if(patient.isPresent()) {
//            logger.info("Patient {} found", id);
//            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient with id " + id + " not found");
//    }



}
