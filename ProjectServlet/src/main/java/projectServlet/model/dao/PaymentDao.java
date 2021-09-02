package projectServlet.model.dao;

import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDao extends GenericDao<Payment>{
    List<Payment> findByUserId(Long userId);

    List<Payment> findByUserId(Long userId, int page, int pageSize, String sortBy, boolean asc);
}
