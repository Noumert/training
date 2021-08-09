package project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.model.dto.UserAccountDTO;
import project.model.entity.Account;
import project.model.entity.User;
import project.model.service.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@Validated
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
    public String banAccount(@Valid @NotNull Long accountId, Model model){
        try {
            accountService.setBanById(true,accountId);
            return "redirect:/admin/accounts";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/admin/accountBanResult";
        }
    }

    @PostMapping("/unban")
    public String unbanAccount(@Valid @NotNull Long accountId, Model model){
        try {
            accountService.setBanById(false,accountId);
            return "redirect:/admin/accounts";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/admin/accountBanResult";
        }
    }
}

