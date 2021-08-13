package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.entity.Payment;
import project.entity.StatusType;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByAccountIdIn(List<Long> accountIds);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update Payment p set p.status = ?1 where p.id = ?2")
    void setStatusById(StatusType status, Long paymentId);

    List<Payment> findByAccountIdInOrderByPaymentNumber(List<Long> accountIds);

    List<Payment> findByAccountIdInOrderByDateTimeAsc(List<Long> accountIds);

    List<Payment> findByAccountIdInOrderByDateTimeDesc(List<Long> accountIds);
}
