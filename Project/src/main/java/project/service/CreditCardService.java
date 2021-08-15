package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.CreditCard;
import project.repository.CreditCardRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreditCardService {
    final static int MIN_RANDOM = 1000;
    final static int MAX_RANDOM = 9999;
    final static int EXPIRED_DURATION = 5;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    public void saveNewCard(CreditCard creditCard) {
        creditCard.setCardNumber(randomCardNumber());
        creditCard.setExpirationDate(LocalDate.now().plusYears(EXPIRED_DURATION));
        try {
            creditCardRepository.save(creditCard);
        } catch (Exception e){
            throw new RuntimeException("problem with save");
        }
    }

    public void save(CreditCard creditCard) {
        try {
            creditCardRepository.save(creditCard);
        } catch (Exception e){
            throw new RuntimeException("problem with save");
        }
    }

    private String randomCardNumber() {
        return UUID.randomUUID().toString();
    }


    public List<CreditCard> findUserCards(Long userId) throws NotFoundException {
        return creditCardRepository
                .findByUserId(userId);
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> findByAccountId(Long accountId){
        return creditCardRepository.findByAccountId(accountId);
    }

}
