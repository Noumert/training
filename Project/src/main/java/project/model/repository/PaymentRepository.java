package project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.model.entity.Account;
import project.model.entity.Payment;
import project.model.entity.StatusType;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByAccountIdIn(List<Long> accountIds);

    @Modifying
    @Query("update Payment p set p.status = ?1 where p.id = ?2")
    void setStatusById(StatusType status, Long paymentId);
}