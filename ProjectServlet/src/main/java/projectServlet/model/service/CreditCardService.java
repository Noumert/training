package projectServlet.model.service;

import projectServlet.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface CreditCardService {

    void save(CreditCard creditCard);

    List<CreditCard> findUserCardsById(Long userId);

    List<CreditCard> findAll();

    Optional<CreditCard> findByAccountId(Long accountId);

}
