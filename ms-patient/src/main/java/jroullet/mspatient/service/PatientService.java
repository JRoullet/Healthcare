package jroullet.mspatient.service;

import jroullet.mspatient.model.Patient;
import jroullet.mspatient.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(Patient patient) {
        patientRepository.save(patient);
        return patient;
    }

    public Boolean updatePatient(Patient updatedPatient) {
        Patient patient = patientRepository.findByEmail(updatedPatient.getEmail());
        if(patient.getFirstName().equals(updatedPatient.getFirstName()) && patient.getLastName().equals(updatedPatient.getLastName())) {
            patient.setFirstName(patient.getFirstName());
            patient.setLastName(patient.getLastName());
            patient.setEmail(patient.getEmail());
            patient.setGender(patient.getGender());
            patient.setBirthday(patient.getBirthday());
            patient.setAddress(patient.getAddress());
            patient.setPhone(patient.getPhone());
            patientRepository.save(patient);
            return true;
        }
        return false;
    }

    public ResponseEntity<Patient> findPatientById(Long id) {
        if (patientRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(patientRepository.findById(id).get());
        }
        return ResponseEntity.notFound().build();
    }
}
