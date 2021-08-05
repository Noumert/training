package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dto.AccountDto;
import project.entity.Account;
import project.entity.CreditCard;
import project.entity.User;
import project.exceptions.DuplicatedNumberException;
import project.repository.CreditCardRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
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

    @Transactional
    public void saveNewCard(Long accountId) throws NotFoundException, DuplicatedNumberException {
        User user = userService.getCurrentUser();
        Account account = accountService.findById(accountId);
        try {
            creditCardRepository.save(CreditCard.builder()
                    .cardNumber(generateCardNumber())
                    .expirationDate(LocalDate.now().plusYears(EXPIRED_DURATION))
                    .account(account)
                    .user(user)
                    .build());
        } catch (Exception e) {
            throw new DuplicatedNumberException("same number exist");
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
                .findByUserId(userService.getCurrentUser()
                        .getId());
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

//    public List<UserCardDTO> findAllUserCard() {
//        return creditCardRepository.findAllUserCard();
//    }
}
