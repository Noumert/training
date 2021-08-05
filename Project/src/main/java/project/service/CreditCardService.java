package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    final static long START_MONEY_VALUE = 0L;
    final static int EXPIRED_DURATION = 5;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveNewCard() throws NotFoundException, DuplicatedNumberException {
        User user = userService.getCurrentUser().orElseThrow(() -> new NotFoundException("no such user"));
        try {
            creditCardRepository.save(CreditCard.builder()
                    .cardNumber(generateCardNumber())
                    .expirationDate(LocalDate.now().plusYears(EXPIRED_DURATION))
                    .money(START_MONEY_VALUE)
                    .active(false)
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
        return random(MIN_RANDOM, MAX_RANDOM)
                + "-" + random(MIN_RANDOM, MAX_RANDOM)
                + "-" + random(MIN_RANDOM, MAX_RANDOM)
                + "-" + random(MIN_RANDOM, MAX_RANDOM);
    }

    private int random(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }


    @Transactional
    public List<CreditCard> findCurrentUserCards() throws NotFoundException {
        return creditCardRepository
                .findByUserId(userService.getCurrentUser()
                        .orElseThrow(() -> new NotFoundException("no such user"))
                        .getId());
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

//    public List<UserCardDTO> findAllUserCard() {
//        return creditCardRepository.findAllUserCard();
//    }
}
