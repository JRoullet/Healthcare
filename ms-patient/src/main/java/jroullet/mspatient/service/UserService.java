package jroullet.mspatient.service;

import jroullet.mspatient.model.User;
import jroullet.mspatient.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        logger.info("findByUserName: " + username);
        try {
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                logger.info("User found");
                return existingUser;
            }
        }
        catch(Exception e){
            logger.error("Error while finding user: " + username, e);
            throw e;
            }
        return Optional.empty();
    }

    public User createUser(User user) {
       userRepository.save(user);
       return user;
    }

    public Boolean updateUser(User updatedUser) {
        Optional<User> user = userRepository.findByUsername(updatedUser.getUsername());
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }
}
