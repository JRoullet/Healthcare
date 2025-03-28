package jroullet.mswebapp.clients;

import jroullet.mswebapp.dto.UsernameDto;
import jroullet.mswebapp.model.Patient;
import jroullet.mswebapp.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="ms-patient")
public interface PatientFeignClient {

    // PATIENT
        @GetMapping(value = "/patient/all", consumes = "application/json")
        List<Patient> findAll();

        //Find
        @GetMapping(value = "/patient/get/{id}", consumes = "application/json")
        Patient getPatientById(@PathVariable Long id);

        //Create
        @PostMapping(value = "/patient/new", consumes = "application/json")
        Patient createPatient(@RequestBody Patient patient);

        //Update
        @PutMapping(value = "/patient/update", consumes = "application/json")
        Patient updatePatientById(@RequestBody Patient updatedPatient);

    // USER
        @PostMapping(value= "/user/get", consumes = "application/json")
        User findUserByUsername(@RequestBody UsernameDto usernameDto);

        @PostMapping(value = "/user/create", consumes = "application/json")
        User createUser(@RequestBody User user);


}
