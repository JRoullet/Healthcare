package jroullet.mspatient.controller;

import jroullet.mspatient.model.User;
import jroullet.mspatient.model.dto.UserPatchDto;
import jroullet.mspatient.model.dto.UsernameDto;
import jroullet.mspatient.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // How to do a Get with a PostMapping (Map a request to parse Json to a String ('username') format)
    @PostMapping("/get")
//    public ResponseEntity<User> findUserByUsername(@RequestBody Map<String,String> request) {
    public ResponseEntity<User> findUserByUsername(@RequestBody UsernameDto usernameDto) {
//        String username = request.get("username");
        logger.info("finding user " + usernameDto.getUsername());
        return userService.findByUsername(usernameDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping ("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("creating user " + user.getUsername());
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@RequestBody User user) {
//        logger.info("updating user " + user.getUsername());
//        if(userService.updateUser(user)){
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        };
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }

    @PatchMapping("/patch")
    public ResponseEntity<User> patchUser(@RequestBody UserPatchDto userPatchDto) {
        logger.info("patching user " + userPatchDto.getUsername());
        Optional<User> updated = userService.patchUserDto(userPatchDto);
        return updated.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());}

}
