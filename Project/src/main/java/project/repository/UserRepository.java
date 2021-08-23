package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.entity.RoleType;
import project.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 11.08.2021.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(RoleType roleType);
}
