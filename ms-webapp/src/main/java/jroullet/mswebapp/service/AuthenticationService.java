package jroullet.mswebapp.service;

import jroullet.mswebapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Authentication procedure
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override // overriding UserDetailsService (interface) method "loadUserByUsername"
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We load our user from database => call repository to compare entry data with

        try{
            // check if user exists
            Optional<User> user = userService.findByUserName(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException(" User not found: " + username);
            }

            User userToLoad = user.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(userToLoad.getUsername())
                    .password(userToLoad.getPassword())
                    .build();
        } catch (Exception e){
            logger.error("Error Loading user: " + username, e);
            throw new UsernameNotFoundException("Error Loading user: " + username, e);
        }


    }
}
