package pl.zakrzewski.juniorjavajoboffers.domain.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    List<User> getUserByEnabledTrue();

    @Transactional
    String deleteUserById(String id);

    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.enabled = TRUE where a.email = ?1")
    int enableUser(String email);


}
