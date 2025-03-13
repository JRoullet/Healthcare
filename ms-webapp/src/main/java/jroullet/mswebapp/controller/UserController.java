package jroullet.mswebapp.controller;

import jakarta.validation.Valid;
import jroullet.mswebapp.dto.SignInForm;
import jroullet.mswebapp.dto.SignUpForm;
import jroullet.mswebapp.model.User;
import jroullet.mswebapp.service.SessionService;
import jroullet.mswebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/home")
    public ModelAndView showHomeView() {
        User user = sessionService.sessionUser();
        return new ModelAndView ("home", "user", user);
    }

    @GetMapping("/signin")
    public ModelAndView showSignInView() {
        return new ModelAndView ("signin", "signInForm", new SignInForm());
    }


    @PostMapping("/authentication")
    public ModelAndView processSignIn(@Valid @ModelAttribute("signInForm") SignInForm form,
                                      BindingResult result){
        logger.info("Authenticating: " + form.getUsername());
        if(result.hasErrors()){
            return new ModelAndView ("signin", "signInForm", form);
        }
        try {
            Optional<User> optionalUser = userService.findByUserName(form.getUsername());

            if (optionalUser.isEmpty()) {
                logger.info("No such User: " + form.getUsername());
                return new ModelAndView("signin", "signInForm", form)
                        .addObject("authError", "Email not found");
            }
            boolean isAuthenticated = userService.isAuthenticated(form.getUsername(), form.getPassword());

            if (isAuthenticated) {
                User user = optionalUser.get();

                // Authentication from User object
                // UserDetails not needed
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList());

                // Update securityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authentication);

                ModelAndView modelAndView = new ModelAndView("home");
                modelAndView.addObject("user", user);
                return modelAndView;

            } else {
                return new ModelAndView("signin", "signInForm", form)
                        .addObject("authError", "Invalid password");
            }
        }
            catch(UsernameNotFoundException e){
                logger.info("User not found: " + form.getUsername());
                return new ModelAndView("signin", "signInForm", form)
                        .addObject("authError","Email not found");

            }
            catch(Exception e){
                logger.error("Error during authentication", e);
                return new ModelAndView("signin", "signInForm", form)
                        .addObject("authError", "An error occurred during authentication");
            }
    }

    @GetMapping("/signup")
    public ModelAndView showSignUpView() {
        return new ModelAndView("signup", "signUpForm", new SignUpForm());
    }

    @PostMapping("/signup")
    public ModelAndView processSignUp(@Valid @ModelAttribute("signUpForm") SignUpForm form,
                                BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return new ModelAndView("signup", "signUpForm", form);
        }
        try{
            userService.registration(form);
            return new ModelAndView("signin", "signInForm", form);
        }
        catch(RuntimeException e){
            // Redirects an attribute => error to display it => from sign up page to the signin page
            redirectAttributes.addFlashAttribute("authError","Username already exists");
            return new ModelAndView( "redirect:/signin");
        }
    }


    @GetMapping("/logout")
    public ModelAndView showLogout() {
        return new ModelAndView ("logout");
    }

//    @GetMapping("/delete-toto-users")
//    public String deleteTotoUsers() {
//        try {
//            userService.deleteTotoUsers();
//            return "redirect:/account?success=true"; // redirige vers une page de succès
//        } catch (Exception e) {
//            return "redirect:/account?error=true"; // redirige vers une page d'erreur en cas de problème
//        }
//    }


}
