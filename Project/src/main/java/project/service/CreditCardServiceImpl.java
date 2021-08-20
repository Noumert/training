package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.CreditCard;
import project.repository.CreditCardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountServiceImpl accountService;

    public void save(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    public List<CreditCard> findUserCards(Long userId) {
        return creditCardRepository
                .findByUserId(userId);
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> findByAccountId(Long accountId) {
        return creditCardRepository.findByAccountId(accountId);
    }

}
