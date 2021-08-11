package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import project.service.AccountService;
import project.service.CreditCardService;
import project.service.UnbanAccountRequestService;
import project.service.UserService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @RequestMapping()
    public String accountsPage(Model model){
        try {
            Long currentUserId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findUserAccountsByUserId(currentUserId)));
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error",true);
        }
        return "user/accounts";
    }

    @PostMapping("/add")
    public String addAccountCard(Model model){
        Long currentUserId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            User currentUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            Account account = Account.builder()
                    .user(currentUser)
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
    public String banAccount(@NotNull Long accountId, Model model){
        try {
            log.info("unban account from user ban {} accountId {}", true, accountId);
            accountService.setBanById(true,accountId);
            return "redirect:/user/accounts";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/user/accountBanResult";
        }
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long accountId, Model model){
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

    @PostMapping("/topUp")
    public String topUpAccount(@NotNull Long accountId,
                               @NotNull @Min(value = 1L, message = "min top up is 1")
                               @Max(value = 99999L, message = "max top up is 99999") Long money,
                               Model model){
        try {
            log.info("add money accountId {} money {}",accountId,money);
            accountService.addMoneyById(money,accountId);
            model.addAttribute("success",true);
            return "redirect:/user/accounts";
        } catch (NotEnoughMoneyException | RuntimeException | NotFoundException e) {
            model.addAttribute("error", true);
            return "/user/accountTopUpResult";
        }
    }
}
