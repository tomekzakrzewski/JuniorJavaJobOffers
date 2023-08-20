package pl.zakrzewski.juniorjavajoboffers.domain.register;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
