package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.CreditCard;
import project.entity.User;
import project.exceptions.DuplicatedNumberException;
import project.repository.CreditCardRepository;

import java.time.LocalDate;

@Service
public class CreditCardService {
    final static int EXPIRED_DURATION = 5;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    UserService userService;

    public void saveNewCard() throws NotFoundException, DuplicatedNumberException {
        User user = userService.getCurrentUser().orElseThrow(()-> new NotFoundException("no such user"));
        try {
            creditCardRepository.save(CreditCard.builder()
                    .cardNumber("1232-1234-1234-1244")
                    .expirationDate(LocalDate.now().plusYears(EXPIRED_DURATION))
                    .user(user)
                    .build());
        } catch (Exception e){
            throw new DuplicatedNumberException("same number exist");
        }
    }
}
