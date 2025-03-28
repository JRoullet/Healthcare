package jroullet.mswebapp.service;


import feign.FeignException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jroullet.mswebapp.clients.PatientFeignClient;
import jroullet.mswebapp.dto.SignUpForm;
import jroullet.mswebapp.dto.UsernameDto;
import jroullet.mswebapp.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final PatientFeignClient patientFeignClient;
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public Optional<User> findByUserName(UsernameDto username){
        logger.info("findByUserName: " + username);
        try{
            User user = patientFeignClient.findUserByUsername(username);
            logger.info("User found: " + (user != null ? user.getUsername() : "null"));
            return Optional.ofNullable(user);
        }
        catch (FeignException.NotFound e){
            logger.info("User not found: " + username);
            return Optional.empty();
        }
        catch (Exception e){
            logger.error("Error while finding user: " + username, e);
            throw e;
        }

    }

    public User registration(SignUpForm form){

        try{
            Optional<User> existingUser = findByUserName(new UsernameDto(form.getUsername()));
            if(existingUser.isPresent()){
                logger.info("User already exists: " + existingUser);
                throw new RuntimeException("Email already exists");
            }
            User newUser = new User();
            newUser.setUsername(form.getUsername());
            newUser.setPassword(passwordEncoder.encode(form.getPassword()));
            newUser.setRole("USER");


            return patientFeignClient.createUser(newUser);
        }
        catch(Exception e){
            logger.info("Error creating user: " + e);
                throw e;

        }
    }




//    @Transactional
//    public boolean isAuthenticated(@NotBlank(message = "Email needed") @Email(message = "Invalid email") String email, @NotBlank(message = "Password needed") String password) {
//        Optional<User> userOptional = Optional.ofNullable(patientFeignClient.findUserByUsername(email));
//
//        if(userOptional.isEmpty()){
//            return false;
//        }
//        User user = userOptional.get();
//        String storedPassword = user.getPassword();
//
//        if(storedPassword!= null && passwordEncoder.matches(password, storedPassword)){
//            return true;
//        }
//        return false;
//    }
//
//    public Optional<User> getUserByEmail(@NotBlank(message = "Email needed") @Email(message = "Invalid email") String email) {
//         return Optional.ofNullable(userRepository.findUserByEmail(email))
//                 .orElseThrow(()-> new UsernameNotFoundException("User with email " + email +" not found"));
//    }
}

