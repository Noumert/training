package project.repository;

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

    Optional<Account> findByAccountName(String accountName);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update Account a set a.ban = ?1 where a.id = ?2")
    void setBanById(boolean ban, Long accountId);

    List<Account> findByUserIdOrderByAccountName(Long userId);

    List<Account> findByUserIdOrderByAccountNumber(Long userId);

    List<Account> findByUserIdOrderByMoney(Long userId);
}
