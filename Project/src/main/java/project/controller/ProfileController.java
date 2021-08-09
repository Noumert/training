package project.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.model.service.AccountService;
import project.model.service.PaymentService;
import project.model.service.UserService;

@Controller
@RequestMapping("user/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaymentService paymentService;


    @RequestMapping()
    public String paymentsPage(Model model) {
        try {
            model.addAttribute("user",entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortAccountsByMoney")
    public String sortAccountsByMoney(Model model) {
        try {
            model.addAttribute("user",entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortAccountsByName")
    public String sortAccountsByName(Model model) {
        try {
            model.addAttribute("user",entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortAccountsByNumber")
    public String sortAccountsByNumber(Model model) {
        try {
            model.addAttribute("user",entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

}
