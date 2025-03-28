package jroullet.mspatient.repository;

import jroullet.mspatient.model.User;
import jroullet.mspatient.model.dto.UsernameDto;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @SQL("SELECT u FROM USER u WHERE u.username = ?")
    Optional<User> findByUsername(String username);

}
