package projectServlet.model.dao;

import projectServlet.model.entity.CreditCard;
import projectServlet.model.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface CreditCardDao extends GenericDao<CreditCard>{
    List<CreditCard> findByUserId(Long userId);
    Optional<CreditCard> findByAccountId(Long accountId);

}
