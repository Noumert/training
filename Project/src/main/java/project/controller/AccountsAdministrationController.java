package project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dto.UserCardDTO;
import project.entity.Account;
import project.entity.CreditCard;
import project.entity.User;
import project.service.CreditCardService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin/accounts")
public class AccountsAdministrationController {
    @Autowired
    CreditCardService creditCardService;

    @RequestMapping()
    public String creditCardsPage(Model model) {
        List<CreditCard> allCards = creditCardService.findAll();

        List<UserCardDTO> userCardDTOS = allCards
                .stream().map(this::convertCardToDto)
                .collect(Collectors.toList());

        model.addAttribute("userCards", userCardDTOS);

        return "admin/creditCardsAdministration";
    }

    private UserCardDTO convertCardToDto(CreditCard creditCard) {
        User user = creditCard.getUser();
        Account account = creditCard.getAccount();
        return UserCardDTO.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(creditCard.getId())
                .cardNumber(creditCard.getCardNumber())
                .expirationDate(creditCard.getExpirationDate())
                .money(account.getMoney())
                .build();
    }


}

