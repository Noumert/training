package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.model.entity.RoleType;
import project.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(RoleType roleType);

    @Modifying
    @Query("update User u set u.accountNonLocked = ?1 where u.id = ?2")
    void setBanById(boolean accountNonLocked, Long userId);
}
