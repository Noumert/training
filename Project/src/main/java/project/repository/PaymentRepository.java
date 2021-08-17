package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Payment> findByAccountIdIn(List<Long> accountIds, Pageable pageable);
}
