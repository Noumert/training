package project.model.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.model.entity.CreditCard;
import project.model.repository.CreditCardRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    public void saveNewCard(CreditCard creditCard) throws NotFoundException {
        creditCard.setCardNumber(generateCardNumber());
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

    private String generateCardNumber() {
        List<String> cardsNumbers = creditCardRepository.findAll().stream().map(CreditCard::getCardNumber).collect(Collectors.toList());
        String cardNumber = randomCardNumber();
        while (cardsNumbers.contains(cardNumber)) {
            cardNumber = randomCardNumber();
        }
        return cardNumber;
    }

    private String randomCardNumber() {
        return random()
                + "-" + random()
                + "-" + random()
                + "-" + random();
    }

    private String random() {
        return String.valueOf((int) Math.floor(Math.random()
                * (CreditCardService.MAX_RANDOM - CreditCardService.MIN_RANDOM + 1) + CreditCardService.MIN_RANDOM));
    }


    @Transactional
    public List<CreditCard> findCurrentUserCards() throws NotFoundException {
        return creditCardRepository
                .findByUserId(userService
                        .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                        .getId());
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> findByAccountId(Long accountId){
        return creditCardRepository.findByAccountId(accountId);
    }

}
