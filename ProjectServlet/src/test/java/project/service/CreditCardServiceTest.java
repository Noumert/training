package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import projectServlet.model.dao.CreditCardDao;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.entity.CreditCard;
import projectServlet.model.entity.User;
import projectServlet.model.service.CreditCardServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private CreditCardDao creditCardDao;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Test
    public void saveSuccess() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createCreditCardDao()).thenReturn(creditCardDao);
        Mockito.doNothing().when(creditCardDao).save(any(CreditCard.class));

        assertDoesNotThrow(()->creditCardService.save(creditCard));
    }

    @Test
    public void saveException() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createCreditCardDao()).thenReturn(creditCardDao);
        Mockito.doThrow(RuntimeException.class).when(creditCardDao).save(any(CreditCard.class));

        assertThrows(RuntimeException.class, () -> creditCardService.save(creditCard));
    }

    @Test
    void findUserCards() {
        CreditCard creditCard1 = CreditCard.builder()
                .id(1L)
                .build();
        CreditCard creditCard2 = CreditCard.builder()
                .id(2L)
                .build();
        List<CreditCard> creditCards= Arrays.asList(creditCard1,creditCard2);
        Mockito.when(daoFactory.createCreditCardDao()).thenReturn(creditCardDao);
        Mockito.doReturn(creditCards).when(creditCardDao).findByUserId(1L);

        assertThat(creditCardService.findUserCardsById(1L)).isEqualTo(creditCards);
    }

    @Test
    void findAll() {
        CreditCard creditCard1 = CreditCard.builder()
                .id(1L)
                .build();
        CreditCard creditCard2 = CreditCard.builder()
                .id(2L)
                .build();
        List<CreditCard> creditCards= Arrays.asList(creditCard1,creditCard2);
        Mockito.when(daoFactory.createCreditCardDao()).thenReturn(creditCardDao);
        Mockito.doReturn(creditCards).when(creditCardDao).findAll();

        assertThat(creditCardService.findAll()).isEqualTo(creditCards);
    }

    @Test
    void findByAccountId() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createCreditCardDao()).thenReturn(creditCardDao);
        Mockito.doReturn(Optional.of(creditCard)).when(creditCardDao).findByAccountId(1L);

        assertThat(creditCardService.findByAccountId(1L)).isEqualTo(Optional.of(creditCard));
    }
}