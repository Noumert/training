package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import project.entity.CreditCard;
import project.repository.CreditCardRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface CreditCardService {

    void save(CreditCard creditCard);

    List<CreditCard> findUserCards(Long userId);

    List<CreditCard> findAll();

    Optional<CreditCard> findByAccountId(Long accountId);

}
