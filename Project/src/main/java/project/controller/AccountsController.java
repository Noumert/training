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
import project.model.entity.UnbanAccountRequest;
import project.model.entity.User;
import project.model.repository.UnbanAccountRequestRepository;
import project.model.service.AccountService;
import project.model.service.CreditCardService;
import project.model.service.UnbanAccountRequestService;
import project.model.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @Autowired
    UnbanAccountRequestService unbanAccountRequestService;

    @RequestMapping()
    public String accountsPage(Model model){
        try {
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDto(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
        }
        return "user/accounts";
    }

    @PostMapping("/add")
    public String addAccountCard(Model model){
        try {
            User user = userService.getCurrentUser();
            Account account = Account.builder()
                    .user(user)
                    .ban(false)
                    .build();
            accountService.saveNewAccount(account);
            return "redirect:/user/accounts";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
            return "/user/accountAddingResult";
        }
    }

    @PostMapping("/ban")
    public String banAccount(@Valid @NotNull @NotEmpty Long accountId, Model model){
        try {
            accountService.setBanById(true,accountId);
            return "redirect:/user/accounts";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/user/accountBanResult";
        }
    }

    @PostMapping("/unban")
    public String unbanAccount(@Valid @NotNull @NotEmpty Long accountId, Model model){
        //TODO request to admin
        try {
            UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .account(accountService.findById(accountId))
                    .resolved(false)
                    .build();
            unbanAccountRequestService.save(unbanAccountRequest);
            model.addAttribute("success",true);
            return "/user/accountBanResult";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
            return "/user/accountBanResult";
        }
    }

}
