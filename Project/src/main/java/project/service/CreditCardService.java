package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.client.HttpClientErrorException;
import project.dto.UserDTO;
import project.entity.CreditCard;
import project.entity.RoleType;
import project.entity.User;
import project.exceptions.DublicatedEmailException;
import project.repository.CreditCardRepository;
import project.repository.UserRepository;

import java.time.LocalDate;

public class CreditCardService {
    final static int EXPIRED_DURATION = 5;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    UserService userService;

    public void saveNewCard() throws NotFoundException {
        User user = userService.getCurrentUser().orElseThrow(()-> new NotFoundException("no such user"));
        try {
            creditCardRepository.save(CreditCard.builder()
                    .cardNumber("1232-1234-1234-1244")
                    .expirationDate(LocalDate.now().plusYears(EXPIRED_DURATION))
                    .user(user)
                    .build());
        } catch (Exception e){

        }
    }
}
