package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.entity.RoleType;
import project.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(RoleType roleType);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update User u set u.accountNonLocked = ?1 where u.id = ?2")
    void setBanById(boolean accountNonLocked, Long userId);
}
