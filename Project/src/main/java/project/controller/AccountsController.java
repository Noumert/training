package project.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.model.dto.AccountDTO;
import project.model.entity.Account;
import project.model.entity.User;
import project.model.service.AccountService;
import project.model.service.CreditCardService;
import project.model.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/accounts")
public class AccountsController {
    @Autowired
    CreditCardService creditCardService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    EntityDtoConverter entityDtoConverter;

    @RequestMapping()
    public String accountsPage(Model model){
        try {
            model.addAttribute("accounts",entityDtoConverter.convertFromAccountsListToDto(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
        }
        return "user/accounts";
    }

    @PostMapping("/add")
    public String addAccountCard(Model model){
        try {
            accountService.saveNewAccount(createAccount());
            return "redirect:/user/accounts";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
            return "/user/accountAddingResult";
        }
    }

    private Account createAccount() throws NotFoundException {
        User user = userService.getCurrentUser();
        return Account.builder()
                .user(user)
                .build();
    }
}
