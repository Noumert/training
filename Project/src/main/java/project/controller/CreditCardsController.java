package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.entity.Account;
import project.entity.CreditCard;
import project.entity.MyUserDetails;
import project.entity.User;
import project.service.AccountService;
import project.service.CreditCardService;
import project.service.UserService;

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
        Long currentUserId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            model.addAttribute("userCards", entityDtoConverter.convertCardsListToDTO(creditCardService.findUserCards(currentUserId)));
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(
                    accountService.findFreeUserAccountsByUserId(currentUserId)));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/creditCards";
    }

    @PostMapping("/add")
    public String addCreditCard(@NotNull Long accountId, Model model) {
        try {
            Long currentUserId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            User user = userService.findById(currentUserId).orElseThrow(() -> new NotFoundException("no such account"));
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            creditCardService.saveNewCard(CreditCard.builder()
                    .account(account)
                    .user(user)
                    .build());
            return "redirect:/user/creditCards";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error", true);
            return "/user/cardAddingResult";
        }
    }

}
