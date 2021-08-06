package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dto.AccountDTO;
import project.dto.CreditCardDto;
import project.entity.Account;
import project.entity.CreditCard;
import project.entity.User;
import project.service.AccountService;
import project.service.CreditCardService;
import project.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static project.controller.AccountsController.convertFromAccountsListToDto;

@Slf4j
@Controller
@RequestMapping("/user/creditCards")
public class CreditCardsController {
    @Autowired
    CreditCardService creditCardService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @RequestMapping()
    public String creditCardsPage(Model model) {
        try {
            model.addAttribute("userCards", convertFromCardsListToDto(creditCardService.findCurrentUserCards()));
            model.addAttribute("accounts",convertFromAccountsListToDto(accountService.findFreeCurrentUserAccounts()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/creditCards";
    }

    @PostMapping("/add")
    public String addCreditCard(@Valid @NotNull @NotEmpty Long accountId, Model model) {
        try {
            creditCardService.saveNewCard(createCreditCard(accountId));
            return "redirect:/user/creditCards";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error", true);
            return "/user/cardAddingResult";
        }
    }

    public static List<CreditCardDto> convertFromCardsListToDto(List<CreditCard> currentUserCards) {
        return currentUserCards.stream()
                .map(card->CreditCardDto.builder()
                        .id(card.getId())
                        .expirationDate(card.getExpirationDate())
                        .cardNumber(card.getCardNumber())
                        .money(card.getAccount().getMoney())
                        .build()).collect(Collectors.toList());
    }

    private CreditCard createCreditCard(Long accountId) throws NotFoundException {
        User user = userService.getCurrentUser();
        Account account = accountService.findById(accountId);
        return CreditCard.builder()
                .account(account)
                .user(user)
                .build();
    }
}
