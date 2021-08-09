package project.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.exceptions.NotEnoughMoneyException;
import project.model.EntityDtoConverter;
import project.model.PaymentsAndAccountsSorter;
import project.model.entity.Account;
import project.model.entity.Payment;
import project.model.service.AccountService;
import project.model.service.PaymentService;
import project.model.service.UserService;

import javax.validation.constraints.NotNull;

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
    @Autowired
    private PaymentsAndAccountsSorter paymentsAndAccountsSorter;


    @RequestMapping()
    public String paymentsPage(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService.findCurrentUserPayments()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/send")
    public String send(@NotNull Long paymentId, Model model) {
        try {
            Payment payment = paymentService.findById(paymentId);
            Account account = payment.getAccount();
            if(account.getMoney()<payment.getMoney()){
                throw new NotEnoughMoneyException("not enough money");
            }
            paymentService.sendPaymentById(paymentId);
            return "redirect:/user/profile";
        } catch (NotFoundException | NotEnoughMoneyException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
            return "user/paymentSendResult";
        }
    }

    @RequestMapping("/sortAccountsByMoney")
    public String sortAccountsByMoney(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",
                    entityDtoConverter.convertAccountsListToDTO(
                            paymentsAndAccountsSorter.sortAccountsByMoney(
                                    accountService.findCurrentUserAccounts())));
            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService.findCurrentUserPayments()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortAccountsByName")
    public String sortAccountsByName(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",
                    entityDtoConverter.convertAccountsListToDTO(
                            paymentsAndAccountsSorter.sortAccountsByName(
                                    accountService.findCurrentUserAccounts())));
            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService.findCurrentUserPayments()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortAccountsByNumber")
    public String sortAccountsByNumber(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts",
                    entityDtoConverter.convertAccountsListToDTO(
                            paymentsAndAccountsSorter.sortAccountsByNumber(
                                    accountService.findCurrentUserAccounts())));
            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService.findCurrentUserPayments()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortPaymentsByNumber")
    public String sortPaymentsByNumber(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
            model.addAttribute("payments",
                    entityDtoConverter.convertPaymentsListToDTO(
                            paymentsAndAccountsSorter.sortPaymentsByNumber(paymentService.findCurrentUserPayments())));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortPaymentsByDataDESC")
    public String sortPaymentsByDataDESC(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
            model.addAttribute("payments",
                    entityDtoConverter.convertPaymentsListToDTO(
                            paymentsAndAccountsSorter.sortPaymentsByDataDESC(paymentService.findCurrentUserPayments())));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/sortPaymentsByDataASC")
    public String sortPaymentsByDataASC(Model model) {
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService.getCurrentUser()));
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
            model.addAttribute("payments",
                    entityDtoConverter.convertPaymentsListToDTO(
                            paymentsAndAccountsSorter.sortPaymentsByDataASC(paymentService.findCurrentUserPayments())));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

}
