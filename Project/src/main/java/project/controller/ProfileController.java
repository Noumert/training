package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.exceptions.NotEnoughMoneyException;
import project.model.EntityDtoConverter;
import project.entity.MyUserDetails;
import project.service.AccountService;
import project.service.PaymentService;
import project.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("user/profile")
public class ProfileController {
    private static final String SORT_DEFAULT = "SORT_DEFAULT";
    private static final String SORT_ACCOUNTS_BY_MONEY = "SORT_ACCOUNTS_BY_MONEY";
    private static final String SORT_ACCOUNTS_BY_NAME = "SORT_ACCOUNTS_BY_NAME";
    private static final String SORT_ACCOUNTS_BY_NUMBER = "SORT_ACCOUNTS_BY_NUMBER";
    private static final String SORT_PAYMENTS_BY_NUMBER = "SORT_PAYMENTS_BY_NUMBER";
    private static final String SORT_PAYMENTS_BY_DATE_ASC = "SORT_PAYMENTS_BY_DATE_ASC";
    private static final String SORT_PAYMENTS_BY_DATE_DESC = "SORT_PAYMENTS_BY_DATE_DESC";

    @Autowired
    private UserService userService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaymentService paymentService;


    @RequestMapping()
    public String paymentsPage(Model model, String sortAccounts, String sortPayments) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<String> sortAccountsOpt = Optional.ofNullable(sortAccounts);
        Optional<String> sortPaymentsOpt = Optional.ofNullable(sortPayments);
        log.info("sortAccounts {},sortPayments {}", sortAccountsOpt, sortPaymentsOpt);
        try {
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));

            switch (sortAccountsOpt.orElse(SORT_DEFAULT)) {
                case SORT_ACCOUNTS_BY_MONEY:
                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
                            .findUserAccountsByUserIdOrderByMoney(currentUserId)));
                    break;
                case SORT_ACCOUNTS_BY_NAME:
                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
                            .findUserAccountsByUserIdOrderByAccountName(currentUserId)));
                    break;
                case SORT_ACCOUNTS_BY_NUMBER:
                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
                            .findUserAccountsByUserIdOrderByAccountNumber(currentUserId)));
                    break;
                default:
                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
                            .findUserAccountsByUserId(currentUserId)));

                    break;
            }

            switch (sortPaymentsOpt.orElse(SORT_DEFAULT)) {
                case SORT_PAYMENTS_BY_NUMBER:
                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
                            .findUserPaymentsByUserIdOrderByPaymentNumber(currentUserId)));
                    break;
                case SORT_PAYMENTS_BY_DATE_ASC:
                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
                            .findUserPaymentsByUserIdOrderByDateTimeAsc(currentUserId)));
                    ;
                    break;
                case SORT_PAYMENTS_BY_DATE_DESC:
                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
                            .findUserPaymentsByUserIdOrderByDateTimeDesc(currentUserId)));
                    break;
                default:
                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
                            .findUserPaymentsByUserId(currentUserId)));
                    break;
            }

        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/profile";
    }

    @RequestMapping("/send")
    public String send(@NotNull Long paymentId, Model model) {
        try {
            paymentService.sendPayment(paymentService.findById(paymentId));
            return "redirect:/user/profile";
        } catch (NotFoundException | NotEnoughMoneyException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
            return "user/paymentSendResult";
        }
    }

//    @RequestMapping("/sortAccountsByMoney")
//    public String sortAccountsByMoney(Model model) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        try {
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
//                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));
//            model.addAttribute("accounts",
//                    entityDtoConverter.convertAccountsListToDTO(
//                            paymentsAndAccountsSorter.sortAccountsByMoney(
//                                    accountService.findUserAccountsByUserId(currentUserId))));
//            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService.findUserPaymentsByUserId(currentUserId)));
//        } catch (NotFoundException | UnexpectedRollbackException e) {
//            model.addAttribute("error", true);
//        }
//        return "user/profile";
//    }
//
//    @RequestMapping("/sortAccountsByName")
//    public String sortAccountsByName(Model model) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        try {
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
//                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));
//            model.addAttribute("accounts",
//                    entityDtoConverter.convertAccountsListToDTO(
//                            paymentsAndAccountsSorter.sortAccountsByName(
//                                    accountService.findUserAccountsByUserId(currentUserId))));
//            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
//                    .findUserPaymentsByUserId(currentUserId)));
//        } catch (NotFoundException | UnexpectedRollbackException e) {
//            model.addAttribute("error", true);
//        }
//        return "user/profile";
//    }
//
//    @RequestMapping("/sortAccountsByNumber")
//    public String sortAccountsByNumber(Model model) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        try {
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
//                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));
//            model.addAttribute("accounts",
//                    entityDtoConverter.convertAccountsListToDTO(
//                            paymentsAndAccountsSorter.sortAccountsByNumber(
//                                    accountService.findUserAccountsByUserId(currentUserId))));
//            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(
//                    paymentService.findUserPaymentsByUserId(currentUserId)));
//        } catch (NotFoundException | UnexpectedRollbackException e) {
//            model.addAttribute("error", true);
//        }
//        return "user/profile";
//    }
//
//    @RequestMapping("/sortPaymentsByNumber")
//    public String sortPaymentsByNumber(Model model) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        try {
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
//                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));
//            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService.findUserAccountsByUserId(currentUserId)));
//            model.addAttribute("payments",
//                    entityDtoConverter.convertPaymentsListToDTO(
//                            paymentsAndAccountsSorter.sortPaymentsByNumber(paymentService.findUserPaymentsByUserId(currentUserId))));
//        } catch (NotFoundException | UnexpectedRollbackException e) {
//            model.addAttribute("error", true);
//        }
//        return "user/profile";
//    }
//
//    @RequestMapping("/sortPaymentsByDataDESC")
//    public String sortPaymentsByDataDESC(Model model) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        try {
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
//                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));
//            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
//                    accountService.findUserAccountsByUserId(currentUserId)));
//            model.addAttribute("payments",
//                    entityDtoConverter.convertPaymentsListToDTO(
//                            paymentsAndAccountsSorter.sortPaymentsByDataDESC(
//                                    paymentService.findUserPaymentsByUserId(currentUserId))));
//        } catch (NotFoundException | UnexpectedRollbackException e) {
//            model.addAttribute("error", true);
//        }
//        return "user/profile";
//    }
//
//    @RequestMapping("/sortPaymentsByDataASC")
//    public String sortPaymentsByDataASC(Model model) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        try {
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(userService
//                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())));
//            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
//                    accountService.findUserAccountsByUserId(currentUserId)));
//            model.addAttribute("payments",
//                    entityDtoConverter.convertPaymentsListToDTO(
//                            paymentsAndAccountsSorter.sortPaymentsByDataASC(
//                                    paymentService.findUserPaymentsByUserId(currentUserId))));
//        } catch (NotFoundException | UnexpectedRollbackException e) {
//            model.addAttribute("error", true);
//        }
//        return "user/profile";
//    }

}
