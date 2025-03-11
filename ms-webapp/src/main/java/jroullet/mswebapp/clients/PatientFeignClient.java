package jroullet.mswebapp.clients;

import jroullet.mswebapp.model.PatientDto;
import jroullet.mswebapp.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="ms-patient")
public interface PatientFeignClient {

//    PATIENT PART
    @PostMapping(value="patient", consumes = "application/json")
    PatientDto getPatientByMail(@RequestBody PatientDto patientDto);

//    USER PART
    @PostMapping(value= "/user/get", consumes = "application/json")
    User findUserByUsername(@RequestBody String username);

    @PostMapping(value = "/user/create", consumes = "application/json")
    User createUser(@RequestBody User user);


}
