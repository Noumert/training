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

@Service
public class CreditCardService {
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
                    .cardNumber("1233-1234-1234-1244")
                    .expirationDate(LocalDate.now().plusYears(EXPIRED_DURATION))
                    .money(START_MONEY_VALUE)
                    .active(false)
                    .user(user)
                    .build());
        } catch (Exception e) {
            throw new DuplicatedNumberException("same number exist");
        }
    }

    @Transactional
    public List<CreditCard> findCurrentUserCards() throws NotFoundException {
        return creditCardRepository
                .findByUserId(userService.getCurrentUser()
                .orElseThrow(() -> new NotFoundException("no such user"))
                .getId());
    }
}
