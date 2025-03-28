package jroullet.msdiabete.client;


import jroullet.msdiabete.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-patient")
public interface PatientFeignClient {

        //Find
        @GetMapping(value = "/patient/get/{id}", consumes = "application/json")
        Patient getPatientById(@PathVariable("id") Long id);


}
