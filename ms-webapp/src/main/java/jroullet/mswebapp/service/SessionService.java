package jroullet.mswebapp.service;


import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.dto.UsernameDto;
import jroullet.mswebapp.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {


    private final PatientFeignClient patientFeignClient;

    public SessionService(PatientFeignClient patientFeignClient) {
        this.patientFeignClient = patientFeignClient;
    }

    public User sessionUser() {
        org.springframework.security.core.userdetails.User springUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return patientFeignClient.findUserByUsername(new UsernameDto(springUser.getUsername()));
    }
}
