package jroullet.mspatient.service;

import jroullet.mspatient.model.Patient;
import jroullet.mspatient.model.dto.PatientId;
import jroullet.mspatient.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final static Logger logger = LoggerFactory.getLogger(PatientService.class.getName());

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(Patient patient) {
        patientRepository.save(patient);
        logger.info("Patient Created");
        return patient;
    }

    public Boolean updatePatient(Patient updatedPatient) {

        // Patient object is not null
        if(updatedPatient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }

        // Patient Id is included
        Long patientId = updatedPatient.getId();
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }

        try{
            Optional<Patient> existingPatientOpt = patientRepository.findById(updatedPatient.getId());
            if(existingPatientOpt.isPresent()) {
                Patient existingPatient = existingPatientOpt.get();
                // Avoid code repetition and copy each property of updated patient to existing patient, except id (ignored)
                BeanUtils.copyProperties(updatedPatient, existingPatient, "id");

                patientRepository.save(existingPatient);
                logger.info("Patient Updated");
                return true;
            }
            return false;
        }
        catch (DataAccessException e) {
                throw new RuntimeException("Error updating patient " + updatedPatient.getId() + " in database", e);
            }
        catch (IllegalArgumentException e){
            throw new RuntimeException("Error copying patient properties " + updatedPatient.getId(), e);
        }
//          Map version
//        try {
//            return patientRepository.findById(patientId)
//                    .map(existingPatient -> {
//                        BeanUtils.copyProperties(updatedPatient, existingPatient, "id");
//
//                        patientRepository.save(existingPatient);
//                        logger.info("Patient Updated: {}", patientId);
//                        return true;
//                    })
//                    .orElse(false);
//        } catch (DataAccessException e) {
//            throw new RuntimeException("Error updating patient " + patientId + " in database", e);
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Error copying patient properties for patient " + patientId, e);

    }

    public Optional<Patient> findPatientById(PatientId patientId) {
        return patientRepository.findById(patientId.getId());
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
