package jroullet.mspatient.controller;

import jroullet.mspatient.model.Patient;
import jroullet.mspatient.service.PatientService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final Logger logger = LoggerFactory.getLogger(PatientController.class);


    @PostMapping("/get")
    public ResponseEntity<Patient> getPatientById(@RequestBody Long patientId) {
        logger.info("Patient Found");
        return patientService.findPatientById(patientId);
    }

    @PostMapping("/create")
    public ResponseEntity<Patient> createPatient(Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        logger.info("created patient");
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient updatedPatient) {
        patientService.updatePatient(updatedPatient);
        if (patientService.updatePatient(updatedPatient)) {
            logger.info("updated patient");
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        }
        logger.info("patient could not be updated");
        return new ResponseEntity<>(updatedPatient, HttpStatus.NOT_FOUND);
    }
}
