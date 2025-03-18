package jroullet.mswebapp.controller;

import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.dto.PatientId;
import jroullet.mswebapp.model.Patient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientFeignClient patientFeignClient;
    private final static Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping("/patient/new")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "add-patient";
    }

    @PostMapping("/patient/new")
    public String addPatient(@ModelAttribute Patient patient, Model model){
        Patient createdPatient = patientFeignClient.createPatient(patient);
        if(createdPatient != null){
            return "redirect:/home";
        }
        model.addAttribute("error", "Error creating patient");
        return "add-patient";
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdatePatientForm(@PathVariable("id") Long id, Model model) {
        PatientId patientId = new PatientId();
        patientId.setId(id);
        Patient patient = patientFeignClient.getPatientById(patientId);
        if (patient == null) {
            throw new RuntimeException("Patient not found with ID : " + id);
        }
        model.addAttribute("patient", patient);
        return "update-patient";
    }

    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @ModelAttribute Patient patient, Model model) {
        patient.setId(id);
        Patient updatedPatient = patientFeignClient.updatePatientById(patient);
        if (updatedPatient != null) {
            return "redirect:/home";
        }
        model.addAttribute("error", "Error updating patient");
        return "update-patient";
    }
}
