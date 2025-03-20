package jroullet.mswebapp.controller;

import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.model.Patient;
import jroullet.mswebapp.model.User;
import jroullet.mswebapp.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SessionService sessionService;
    private final PatientFeignClient patientFeignClient;
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/home")
    public String showHomeView(Model model) {
        User user = sessionService.sessionUser();
        List<Patient> patients = patientFeignClient.findAll();
        model.addAttribute("patients", patients);
//        model.addAttribute("patient", new Patient());
        model.addAttribute("user", user);

        return "home";
    }

}
