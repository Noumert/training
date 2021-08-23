package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.entity.Payment;

import java.util.List;

/**
 * Created by Noumert on 11.08.2021.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
//    List<Payment> findByAccountIdIn(List<Long> accountIds);
//
//    Page<Payment> findByAccountIdIn(List<Long> accountIds, Pageable pageable);

    @Query("select p from Payment p left join Account a on a.id=p.account.id where a.user.id = :userId")
    List<Payment> findPaymentsByUserId(Long userId);

    @Query("select p from Payment p left join Account a on a.id=p.account.id where a.user.id = :userId")
    Page<Payment> findPaymentsByUserId(Long userId, Pageable pageable);
}
