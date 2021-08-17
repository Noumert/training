package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            model.addAttribute("userCards", entityDtoConverter.convertCardsListToDTO(creditCardService.findUserCards(currentUserId)));
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
                    accountService.findFreeUserAccountsByUserId(currentUserId)));
        } catch (UnexpectedRollbackException e) {
            log.info("something went wrong with transaction findFreeUserAccountsByUserId");
            model.addAttribute("error", true);
        }
        return "user/creditCards";
    }

    @PostMapping("/add")
    public String addCreditCard(@NotNull Long accountId, Model model, RedirectAttributes redirectAttributes) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            User user = userService.findById(currentUserId).orElseThrow(() -> new NotFoundException("no such account"));
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            creditCardService.saveNewCard(CreditCard.builder()
                    .account(account)
                    .user(user)
                    .build());
            log.info("add new card with account id {}", accountId);
            return "redirect:/user/creditCards";
        } catch (NotFoundException e) {
            log.info("no user with Id {}, or account with Id {}", currentUserId, accountId);
            redirectAttributes.addAttribute("noUserOrAccountError", true);
        } catch (RuntimeException e) {
            log.info("some generated parameter was not unique when trying to add card with account id {}", accountId);
            redirectAttributes.addAttribute("duplicatedError", true);
        }
        return "redirect:/user/creditCards/add";
    }

    @GetMapping("/add")
    public String addCreditCardGet(Model model) {

        model.addAttribute("noUserOrAccountError", true);

        model.addAttribute("duplicatedError", true);

        return "/user/cardAddingResult";
    }

}
