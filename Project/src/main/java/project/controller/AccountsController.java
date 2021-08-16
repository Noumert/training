package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dto.AccountDTO;
import project.dto.TopUpDTO;
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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
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
    @Autowired
    private ControllerUtils controllerUtils;

    @RequestMapping()
    public String accountsPage(Model model) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findUserAccountsByUserId(currentUserId)));
        model.addAttribute("topUp", new TopUpDTO());

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
            log.info("save new account");
            return "redirect:/user/accounts";
        } catch (NotFoundException e) {
            log.info("no user when try to save new account");
            model.addAttribute("noUserError", true);
        } catch (RuntimeException e) {
            log.info("something went wrong with save new account");
            model.addAttribute("duplicatedError", true);
        }
        return "/user/accountAddingResult";
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long accountId, Model model) {
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountService.setBanById(true, account);
            log.info("unban account from user ban {} accountId {}", true, accountId);
            return "redirect:/user/accounts";
        } catch (RuntimeException e) {
            log.info("something went wrong with ban account ban {} accountId {}", true, accountId);
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            model.addAttribute("noAccountError", true);
        }
        return "/admin/accountBanResult";
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long accountId, Model model) {
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .account(account)
                    .resolved(false)
                    .build();
            unbanAccountRequestService.save(unbanAccountRequest);
            model.addAttribute("success", true);
            log.info("unban request sent ban {} accountId {}", true, accountId);
            return "/user/accountBanResult";
        } catch (RuntimeException e) {
            log.info("something went wrong with unban request account ban {} accountId {}", false, accountId);
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            model.addAttribute("noAccountError", true);
        }
        return "/admin/accountBanResult";
    }

    @PostMapping("/topUpForm")
    public String topUpAccountForm(@ModelAttribute("topUp") TopUpDTO topUpDTO,
                                   Model model) {
        model.addAttribute("topUp", topUpDTO);
        try {
            Account account = accountService.findById(topUpDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
            model.addAttribute("account", entityDtoConverter.convertAccountToAccountDTO(account));
        } catch (NotFoundException e) {
            model.addAttribute("noAccountError", true);
            return "/user/accountTopUpResult";
        }
        return "user/accountTopUpForm";
    }

    @PostMapping("/topUpForm/topUp")
    public String topUpAccount(@ModelAttribute("topUp") @Valid TopUpDTO topUpDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = controllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            try {
                Account account = accountService.findById(topUpDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
                model.addAttribute("account", entityDtoConverter.convertAccountToAccountDTO(account));
            } catch (NotFoundException e) {
                model.addAttribute("noAccountError", true);
                return "/user/accountTopUpResult";
            }
            return "user/accountTopUpForm";
        } else {
            long moneyValue = moneyParser.getMoneyValue(topUpDTO.getTopUpMoney());
            try {
                Account account = accountService.findById(topUpDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
                accountService.addMoneyById(moneyValue, account);
                log.info("add moneyValue accountId {} money {}", topUpDTO.getAccountId(), moneyValue);
                return "redirect:/user/accounts";
            } catch (RuntimeException e) {
                log.info("something went wrong when try to add moneyValue accountId {} money {}", topUpDTO.getAccountId(), moneyValue);
                model.addAttribute("error", true);
            }catch (NotEnoughMoneyException e) {
                log.info("money become negative");
                model.addAttribute("noMoneyError", true);
            }
            catch (NotFoundException e) {
                model.addAttribute("noAccountError", true);
            }
            return "/user/accountTopUpResult";
        }
    }
}
