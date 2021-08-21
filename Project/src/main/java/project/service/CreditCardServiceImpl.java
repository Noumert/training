package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.CreditCard;
import project.repository.CreditCardRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountServiceImpl accountService;

    @Override
    public CreditCard save(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public List<CreditCard> findUserCardsById(Long userId) {
        return creditCardRepository
                .findByUserId(userId);
    }

    @Override
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public Optional<CreditCard> findByAccountId(Long accountId) {
        return creditCardRepository.findByAccountId(accountId);
    }

}
