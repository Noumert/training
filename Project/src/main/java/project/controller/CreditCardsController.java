package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.model.entity.Account;
import project.model.entity.CreditCard;
import project.model.entity.User;
import project.model.service.AccountService;
import project.model.service.CreditCardService;
import project.model.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Slf4j
@Controller
@RequestMapping("/user/creditCards")
public class CreditCardsController {
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @RequestMapping()
    public String creditCardsPage(Model model) {
        try {
            model.addAttribute("userCards", entityDtoConverter.convertCardsListToDTO(creditCardService.findCurrentUserCards()));
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findFreeCurrentUserAccounts()));
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

    private CreditCard createCreditCard(Long accountId) throws NotFoundException {
        User user = userService.getCurrentUser();
        Account account = accountService.findById(accountId);
        return CreditCard.builder()
                .account(account)
                .user(user)
                .build();
    }
}
