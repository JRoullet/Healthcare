package jroullet.mspatient.service;

import jroullet.mspatient.model.User;
import jroullet.mspatient.model.dto.UserPatchDto;
import jroullet.mspatient.model.dto.UsernameDto;
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

    public Optional<User> findByUsername(UsernameDto usernameDto) {
        logger.info("findByUserName: " + usernameDto);
        Optional<User> existingUser = userRepository.findByUsername(usernameDto.getUsername());
        if (existingUser.isPresent()) {
                logger.info("User found");
                return existingUser;
        }
        logger.error("Error while finding user: {}", usernameDto.getUsername());
    return Optional.empty();
    }

    public User createUser(User user) {
       userRepository.save(user);
       return user;
    }

//    public Boolean updateUser(User user) {
//        Optional<User> existingOpt = userRepository.findByUsername(user.getUsername());
//        if (existingOpt.isPresent()) {
//            Long existingId = existingOpt.get().getId();
//            user.setId(existingId);
//
//            userRepository.save(user);
//            return true;
//        }
//        return false;
//    }


    public Optional<User> patchUserDto(UserPatchDto dto) {
        return userRepository.findByUsername(dto.getUsername())
                .map(user -> {
                    if (dto.getPassword() != null) user.setPassword(dto.getPassword());
                    if (dto.getRole() != null) user.setRole(dto.getRole());
                    return userRepository.save(user);
                });
    }

}
