package jroullet.mspatient.controller;

import jroullet.mspatient.model.User;
import jroullet.mspatient.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/get")
    public ResponseEntity<User> findUserByUsername(@RequestBody String username) {
        logger.info("finding user " + username);
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping ("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("creating user " + user.getUsername());
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        logger.info("updating user " + user.getUsername());
        User updatedUser = new User();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        userService.updateUser(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
