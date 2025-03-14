package jroullet.mswebapp.clients;

import jroullet.mswebapp.dto.PatientId;
import jroullet.mswebapp.model.Patient;
import jroullet.mswebapp.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="ms-patient")
public interface PatientFeignClient {

//    PATIENT PART

    @GetMapping(value = "/patient/all", consumes = "application/json")
    List<Patient> findAll();

    //Find
    @PostMapping(value = "/patient/get", consumes = "application/json")
    Patient getPatientById(@RequestBody PatientId patientId);

    //Create
    @PostMapping(value = "/patient/new", consumes = "application/json")
    Patient createPatient(@RequestBody Patient patient);

    //Update
    @PutMapping(value = "/patient/update", consumes = "application/json")
    Patient updatePatient(@RequestBody Patient updatedPatient);

//    USER PART
    @PostMapping(value= "/user/get", consumes = "application/json")
    User findUserByUsername(@RequestBody String username);

    @PostMapping(value = "/user/create", consumes = "application/json")
    User createUser(@RequestBody User user);



}
