package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.exceptions.NotEnoughMoneyException;
import project.model.EntityDtoConverter;
import project.entity.Account;
import project.entity.MyUserDetails;
import project.entity.UnbanAccountRequest;
import project.entity.User;
import project.model.MoneyParser;
import project.service.AccountService;
import project.service.CreditCardService;
import project.service.UnbanAccountRequestService;
import project.service.UserService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@Controller
@Validated
@RequestMapping("/user/accounts")
public class AccountsController {
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;
    @Autowired
    private UnbanAccountRequestService unbanAccountRequestService;
    @Autowired
    private MoneyParser moneyParser;

    @RequestMapping()
    public String accountsPage(Model model) {
        try {
            Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findUserAccountsByUserId(currentUserId)));
        } catch (RuntimeException e) {
            model.addAttribute("error", true);
        }
        return "user/accounts";
    }

    @PostMapping("/add")
    public String addAccountCard(Model model) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            User currentUser = userService.findById(currentUserId).orElseThrow(() -> new NotFoundException("no such user"));
            Account account = Account.builder()
                    .user(currentUser)
                    .ban(false)
                    .build();
            accountService.saveNewAccount(account);
            return "redirect:/user/accounts";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error", true);
            return "/user/accountAddingResult";
        }
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long accountId, Model model) {
        try {
            log.info("unban account from user ban {} accountId {}", true, accountId);
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountService.setBanById(true,account);
            return "redirect:/user/accounts";
        } catch (RuntimeException | NotFoundException e) {
            model.addAttribute("error", true);
            return "/user/accountBanResult";
        }
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long accountId, Model model) {
        try {
            UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .account(accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account")))
                    .resolved(false)
                    .build();
            unbanAccountRequestService.save(unbanAccountRequest);
            model.addAttribute("success", true);
            return "/user/accountBanResult";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error", true);
            return "/user/accountBanResult";
        }
    }

    @PostMapping("/topUp")
    public String topUpAccount(@NotNull Long accountId,
                               @NotNull @Min(value = 1, message = "min top up is 1")
                               @Max(value = 99999, message = "max top up is 99999") double money,
                               Model model) {
        long moneyValue = moneyParser.getMoneyValue(money);
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            log.info("add moneyValue accountId {} money {}", accountId, moneyValue);
            accountService.addMoneyById(moneyValue, account);
            model.addAttribute("success", true);
            return "redirect:/user/accounts";
        } catch (NotEnoughMoneyException | RuntimeException | NotFoundException e) {
            model.addAttribute("error", true);
            return "/user/accountTopUpResult";
        }
    }

}
