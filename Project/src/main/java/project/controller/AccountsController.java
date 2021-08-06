package project.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dto.AccountDTO;
import project.entity.Account;
import project.entity.User;
import project.exceptions.DuplicatedNumberException;
import project.service.AccountService;
import project.service.CreditCardService;
import project.service.UserService;

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

    @RequestMapping()
    public String creditCardsPage(Model model){
        try {
            List<AccountDTO> o = convertFromAccountsListToDto(accountService.findCurrentUserAccounts());
            model.addAttribute("accounts", o);
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
        }
        return "user/accounts";
    }

    @PostMapping("/add")
    public String addCreditCard(Model model){
        try {
            accountService.saveNewAccount(createAccount());
            return "redirect:/user/accounts";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
            return "/user/accountAddingResult";
        }
    }

    public static List<AccountDTO> convertFromAccountsListToDto(List<Account> currentUserAccounts) {
        return currentUserAccounts.stream()
                .map(account->AccountDTO.builder()
                        .id(account.getId())
                        .accountName(account.getAccountName())
                        .accountNumber(account.getAccountNumber())
                        .ban(account.isBan())
                        .money(account.getMoney())
                        .build()).collect(Collectors.toList());
    }

    private Account createAccount() throws NotFoundException {
        User user = userService.getCurrentUser();
        return Account.builder()
                .user(user)
                .build();
    }
}
