package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.entity.Account;
import project.model.EntityDtoConverter;
import project.dto.UserAccountDTO;
import project.service.AccountService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/accounts")
public class AccountsAdministrationController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @RequestMapping()
    public String accountsPage(Model model) {

        List<UserAccountDTO> userCardDTOS = entityDtoConverter.convertAccountsToDto(accountService.findAll());
        model.addAttribute("userAccounts", userCardDTOS);

        return "admin/accountsAdministration";
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long accountId, Model model) {
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountService.setBanById(true, account);
            log.info("ban account ban {} accountId {}", true, accountId);
            return "redirect:/admin/accounts";
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
            accountService.setBanById(true, account);
            log.info("unban account ban {} accountId {}", false, accountId);
            return "redirect:/admin/accounts";
        } catch (RuntimeException e) {
            log.info("something went wrong with unban account ban {} accountId {}", false, accountId);
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            model.addAttribute("noAccountError", true);
        }
        return "/admin/accountBanResult";
    }
}

