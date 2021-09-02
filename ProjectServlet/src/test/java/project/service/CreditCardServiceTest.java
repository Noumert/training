package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.CreditCard;
import project.repository.CreditCardRepository;

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
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Test
    public void saveSuccess() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .build();
        Mockito.when(creditCardRepository.save(any(CreditCard.class))).then(returnsFirstArg());

        assertThat(creditCardService.save(creditCard)).isEqualTo(creditCard);
    }

    @Test
    public void saveException() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .build();
        Mockito.when(creditCardRepository.save(any(CreditCard.class))).thenThrow(RuntimeException.class);

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
        Mockito.when(creditCardRepository.findByUserId(1L)).thenReturn(creditCards);

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
        Mockito.when(creditCardRepository.findAll()).thenReturn(creditCards);

        assertThat(creditCardService.findAll()).isEqualTo(creditCards);
    }

    @Test
    void findByAccountId() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .build();
        Mockito.when(creditCardRepository.findByAccountId(1L)).thenReturn(Optional.of(creditCard));

        assertThat(creditCardService.findByAccountId(1L)).isEqualTo(Optional.of(creditCard));
    }
}