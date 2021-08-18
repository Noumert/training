package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.entity.Account;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);

    Page<Account> findByUserId(Long userId, Pageable pageable);

    Optional<Account> findByAccountName(String accountName);

    @Modifying
    @Query("update Account a set a.money = a.money+?2 where a.id = ?1")
    void updateMoneyById(Long id, long money);
}
