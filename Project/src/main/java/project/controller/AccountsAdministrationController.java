package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dto.UserAccountDTO;
import project.entity.Account;
import project.model.EntityDtoConverter;
import project.service.AccountService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Noumert on 11.08.2021.
 */
@Slf4j
@Controller
@RequestMapping("/admin/accounts")
public class AccountsAdministrationController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityDtoConverter<Account, UserAccountDTO> accountUserAccountDtoConverter;


    @RequestMapping()
    public String accountsPage(Model model) {

        List<UserAccountDTO> userCardDTOS = accountUserAccountDtoConverter.convertEntityListToDtoList(accountService.findAll());
        model.addAttribute("userAccounts", userCardDTOS);

        return "admin/accountsAdministration";
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long accountId, Model model, RedirectAttributes redirectAttributes) {
        boolean ban = true;
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountService.setBanByAccount(ban, account);
            log.info("ban account ban {} accountId {}", ban, accountId);
            return "redirect:/admin/accounts";
        } catch (RuntimeException e) {
            log.info("something went wrong with ban account ban {} accountId {}", ban, accountId);
            redirectAttributes.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            redirectAttributes.addAttribute("noAccountError", true);
        }
        return "redirect:/admin/accounts/ban";
    }

    @GetMapping("/ban")
    public String banAccountGet(@NotNull Long accountId, Model model,
                                @RequestParam(required = false,defaultValue = "false") Boolean error,
                                @RequestParam(required = false,defaultValue = "false") Boolean noAccountError) {

        model.addAttribute("error", error);

        model.addAttribute("noAccountError", noAccountError);

        return "/admin/accountBanResult";
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long accountId, Model model) {
        boolean ban = false;
        try {
            Account account = accountService.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountService.setBanByAccount(ban, account);
            log.info("unban account ban {} accountId {}", ban, accountId);
            return "redirect:/admin/accounts";
        } catch (RuntimeException e) {
            log.info("something went wrong with unban account ban {} accountId {}", ban, accountId);
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("account with accountId = {} not found", accountId);
            model.addAttribute("noAccountError", true);
        }
        return "redirect:/admin/accounts/unban";
    }

    @GetMapping("/unban")
    public String unbanAccountGet(@NotNull Long accountId, Model model,
                                  @RequestParam(required = false,defaultValue = "false") Boolean error,
                                  @RequestParam(required = false,defaultValue = "false") Boolean noAccountError) {

        model.addAttribute("error", error);

        model.addAttribute("noAccountError", noAccountError);

        return "/admin/accountBanResult";
    }
}

