package projectServlet.model.dao;

import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDao extends GenericDao<Payment>{
    List<Payment> findPaymentsByUserId(Long userId);


//    Page<Payment> findPaymentsByUserId(Long userId, Pageable pageable);
}
