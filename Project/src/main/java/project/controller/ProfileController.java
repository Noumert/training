package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.entity.User;
import project.exceptions.NotEnoughMoneyException;
import project.model.EntityDtoConverter;
import project.entity.MyUserDetails;
import project.service.AccountService;
import project.service.PaymentService;
import project.service.UserService;

import javax.print.attribute.standard.PageRanges;
import javax.validation.constraints.NotNull;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("user/profile")
public class ProfileController {
    private static final int PAGE_SIZE = 5;
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


//    @RequestMapping()
//    public String paymentsPage(Model model, String sortAccounts, String sortPayments) {
//        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        Optional<String> sortAccountsOpt = Optional.ofNullable(sortAccounts);
//        Optional<String> sortPaymentsOpt = Optional.ofNullable(sortPayments);
//        log.info("sortAccounts {},sortPayments {}", sortAccountsOpt, sortPaymentsOpt);
//        try {
//            User user = userService.findById(currentUserId).orElseThrow(() -> new NotFoundException("no such user"));
//            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(user));
//
//            switch (sortAccountsOpt.orElse(SORT_DEFAULT)) {
//                case SORT_ACCOUNTS_BY_MONEY:
//                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
//                            .findUserAccountsByUserIdOrderByMoney(currentUserId)));
//                    break;
//                case SORT_ACCOUNTS_BY_NAME:
//                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
//                            .findUserAccountsByUserIdOrderByAccountName(currentUserId)));
//                    break;
//                case SORT_ACCOUNTS_BY_NUMBER:
//                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
//                            .findUserAccountsByUserIdOrderByAccountNumber(currentUserId)));
//                    break;
//                default:
//                    model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
//                            .findUserAccountsByUserId(currentUserId)));
//
//                    break;
//            }
//
//            switch (sortPaymentsOpt.orElse(SORT_DEFAULT)) {
//                case SORT_PAYMENTS_BY_NUMBER:
//                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
//                            .findUserPaymentsByUserIdOrderByPaymentNumber(currentUserId)));
//                    break;
//                case SORT_PAYMENTS_BY_DATE_ASC:
//                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
//                            .findUserPaymentsByUserIdOrderByDateTimeAsc(currentUserId)));
//                    ;
//                    break;
//                case SORT_PAYMENTS_BY_DATE_DESC:
//                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
//                            .findUserPaymentsByUserIdOrderByDateTimeDesc(currentUserId)));
//                    break;
//                default:
//                    model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
//                            .findUserPaymentsByUserId(currentUserId)));
//                    break;
//            }
//
//        } catch (UnexpectedRollbackException e) {
//            log.info("error in transaction when open profile");
//            model.addAttribute("error", true);
//        }
//        catch (NotFoundException e) {
//            log.info("no user when open profile");
//            model.addAttribute("noUserError", true);
//        }
//        return "user/profile";
//    }

    @RequestMapping()
    public String paymentsPage(Model model,
                               @RequestParam Optional<Integer> accPage,
                               @RequestParam Optional<String> accSortBy,
                               @RequestParam Optional<Boolean> accAsc,
                               @RequestParam Optional<Integer> payPage,
                               @RequestParam Optional<String> paySortBy,
                               @RequestParam Optional<Boolean> payAsc) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("accPage",accPage.orElse(0));
        model.addAttribute("accSortBy",accSortBy.orElse("id"));
        model.addAttribute("accAsc",accAsc.orElse(true));
        model.addAttribute("payPage",payPage.orElse(0));
        model.addAttribute("paySortBy",paySortBy.orElse("id"));
        model.addAttribute("payAsc",payAsc.orElse(true));
        try {
            User user = userService.findById(currentUserId).orElseThrow(() -> new NotFoundException("no such user"));
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(user));

            Pageable pageableAccount = PageRequest.of(
                    accPage.orElse(0), PAGE_SIZE,
                    accAsc.orElse(true) ? Sort.Direction.ASC : Sort.Direction.DESC, accSortBy.orElse("id"));
            Pageable pageablePayment = PageRequest.of(
                    payPage.orElse(0), PAGE_SIZE,
                    payAsc.orElse(true) ? Sort.Direction.ASC : Sort.Direction.DESC, paySortBy.orElse("id"));

            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(accountService
                    .findUserAccountsByUserId(currentUserId,pageableAccount)));
            model.addAttribute("payments", entityDtoConverter.convertPaymentsListToDTO(paymentService
                    .findUserPaymentsByUserId(currentUserId,pageablePayment)));


        } catch (UnexpectedRollbackException e) {
            log.info("error in transaction when open profile");
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("no user when open profile");
            model.addAttribute("noUserError", true);
        }
        return "user/profile";
    }

    @RequestMapping("/send")
    public String send(@NotNull Long paymentId, Model model) {
        try {
            paymentService.sendPayment(paymentService.findById(paymentId));
            log.info("send payment with id {}", paymentId);
            return "redirect:/user/profile";
        } catch (NotFoundException e) {
            log.info("no payment when send payment with id {}", paymentId);
            model.addAttribute("noPaymentError", true);
        } catch (NotEnoughMoneyException e) {
            log.info("not enough money when send payment with id {}", paymentId);
            model.addAttribute("noMoneyError", true);
        } catch (UnexpectedRollbackException e) {
            log.info("something went wrong with transaction when send payment with id {}", paymentId);
            model.addAttribute("error", true);
        }
        return "user/paymentSendResult";
    }

}
