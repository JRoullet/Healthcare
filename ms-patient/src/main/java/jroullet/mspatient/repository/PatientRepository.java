package jroullet.mspatient.repository;

import jroullet.mspatient.model.Patient;
import org.hibernate.annotations.processing.HQL;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @SQL(value = "SELECT * FROM PATIENT WHERE EMAIL =?")
    Patient findByEmail(String email);

}
