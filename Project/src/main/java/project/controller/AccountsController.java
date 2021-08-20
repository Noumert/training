package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dto.TopUpDTO;
import project.exceptions.BanException;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

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

        return "user/accounts";
    }

    @PostMapping("/add")
    public String addAccountCard(Model model, RedirectAttributes redirectAttributes) {
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
            redirectAttributes.addAttribute("noUserError", true);
        } catch (RuntimeException e) {
            log.info("something went wrong with save new account");
            redirectAttributes.addAttribute("duplicatedError", true);
        }
        return "redirect:/user/accounts/add";
    }

    @GetMapping("/add")
    public String addAccountCardGet(Model model,
                                    @RequestParam(required = false, defaultValue = "false") Boolean noUserError,
                                    @RequestParam(required = false, defaultValue = "false") Boolean duplicatedError) {

        model.addAttribute("noUserError", noUserError);
        model.addAttribute("duplicatedError", duplicatedError);

        return "/user/accountAddingResult";
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long accountId, Model model, RedirectAttributes redirectAttributes) {
        boolean ban = true;
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountService.setBanByAccount(ban, account);
            log.info("unban account from user ban {} accountId {}", ban, accountId);
            return "redirect:/user/accounts";
        } catch (RuntimeException e) {
            log.info("something went wrong with ban account ban {} accountId {}", ban, accountId);
            redirectAttributes.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            redirectAttributes.addAttribute("noAccountError", true);
        }
        return "redirect:/user/accounts/ban";
    }

    @GetMapping("/ban")
    public String banAccountGet(Model model,
                                @RequestParam(required = false, defaultValue = "false") Boolean error,
                                @RequestParam(required = false, defaultValue = "false") Boolean noAccountError) {

        model.addAttribute("error", error);
        model.addAttribute("noAccountError", noAccountError);

        return "/user/accountBanResult";
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long accountId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .account(account)
                    .resolved(false)
                    .build();
            unbanAccountRequestService.save(unbanAccountRequest);
            redirectAttributes.addAttribute("success", true);
            log.info("unban request sent accountId {}", accountId);
        } catch (RuntimeException e) {
            log.info("something went wrong with unban request accountId {}", accountId);
            redirectAttributes.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            redirectAttributes.addAttribute("noAccountError", true);
        }
        return "redirect:/user/accounts/unban";
    }

    @GetMapping("/unban")
    public String unbanAccountGet(Model model,
                                  @RequestParam(required = false, defaultValue = "false") Boolean success,
                                  @RequestParam(required = false, defaultValue = "false") Boolean error,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noAccountError) {

        model.addAttribute("success", success);
        model.addAttribute("error", error);
        model.addAttribute("noAccountError", noAccountError);

        return "/user/accountBanResult";
    }

    @GetMapping("/topUpForm")
    public String topUpAccountForm(@NotNull Long accountId, Model model) {
        model.addAttribute("topUp", new TopUpDTO());
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
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
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = controllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);

            try {
                Account account = accountService.findById(topUpDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
                model.addAttribute("account", entityDtoConverter.convertAccountToAccountDTO(account));
            } catch (NotFoundException e) {
                redirectAttributes.addAttribute("noAccountError", true);
                return "redirect:/user/accounts/topUpResult";
            }

            return "user/accountTopUpForm";
        } else {
            long moneyValue = moneyParser.getMoneyValue(topUpDTO.getTopUpMoney());
            try {
                Account account = accountService.findById(topUpDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
                if (account.isBan()) {
                    throw new BanException("account was banned");
                }
                accountService.addMoneyById(moneyValue, account.getId());
                log.info("add moneyValue accountId {} money {}", topUpDTO.getAccountId(), moneyValue);
                return "redirect:/user/accounts";
            } catch (RuntimeException e) {
                log.info("something went wrong when try to add moneyValue accountId {} money {}", topUpDTO.getAccountId(), moneyValue);
                redirectAttributes.addAttribute("error", true);
            } catch (NotEnoughMoneyException e) {
                log.info("money become negative when top up account with id {}", topUpDTO.getAccountId());
                redirectAttributes.addAttribute("noMoneyError", true);
            } catch (NotFoundException e) {
                redirectAttributes.addAttribute("noAccountError", true);
            } catch (BanException e) {
                log.info("account was banned when top up account with id {}", topUpDTO.getAccountId());
                redirectAttributes.addAttribute("banError", true);
            }

            return "redirect:/user/accounts/topUpForm/topUp";
        }
    }

    @GetMapping("/topUpForm/topUp")
    public String topUpAccountGet(Model model,
                                  @RequestParam(required = false, defaultValue = "false") Boolean error,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noMoneyError,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noAccountError,
                                  @RequestParam(required = false, defaultValue = "false") Boolean banError
                                  ) {

        model.addAttribute("error", error);
        model.addAttribute("noMoneyError", noMoneyError);
        model.addAttribute("noAccountError", noAccountError);
        model.addAttribute("banError", banError);

        return "/user/accountTopUpResult";
    }

    @GetMapping("/topUpResult")
    public String topUpResultGet(Model model,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noAccountError){

        model.addAttribute("noAccountError", noAccountError);

        return "/user/accountTopUpResult";
    }
}

