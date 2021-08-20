package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dto.AccountDTO;
import project.dto.PaymentDTO;
import project.entity.Account;
import project.entity.Payment;
import project.entity.User;
import project.exceptions.BanException;
import project.exceptions.NotEnoughMoneyException;
import project.model.EntityDtoConverterOlolo;
import project.entity.MyUserDetails;
import project.service.*;

import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("user/profile")
public class ProfileController {
    private static final int PAGE_SIZE = 5;

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping()
    public String paymentsPage(Model model,
                               @RequestParam(required = false, defaultValue = "1") Integer accPage,
                               @RequestParam(required = false, defaultValue = "id") String accSortBy,
                               @RequestParam(required = false, defaultValue = "true") Boolean accAsc,
                               @RequestParam(required = false, defaultValue = "1") Integer payPage,
                               @RequestParam(required = false, defaultValue = "id") String paySortBy,
                               @RequestParam(required = false, defaultValue = "true") Boolean payAsc) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("accPage", accPage);
        model.addAttribute("accSortBy", accSortBy);
        model.addAttribute("accAsc", accAsc);
        model.addAttribute("payPage", payPage);
        model.addAttribute("paySortBy", paySortBy);
        model.addAttribute("payAsc", payAsc);
        try {

            User user = userService.findById(currentUserId).orElseThrow(() -> new NotFoundException("no such user"));
            model.addAttribute("user", entityDtoConverter.convertUserToUserDTO(user));

            Pageable pageableAccount = PageRequest.of(
                    accPage - 1, PAGE_SIZE,
                    accAsc ? Sort.Direction.ASC : Sort.Direction.DESC, accSortBy);
            Pageable pageablePayment = PageRequest.of(
                    payPage - 1, PAGE_SIZE,
                    payAsc ? Sort.Direction.ASC : Sort.Direction.DESC, paySortBy);

            Page<Account> accounts = accountService
                    .findByUserId(currentUserId, pageableAccount);
            Page<Payment> payments = paymentService
                    .findUserPaymentsByUserId(currentUserId, pageablePayment);

            Page<AccountDTO> accountDTOS = entityDtoConverter.convertAccountsListToDTO(accounts);
            Page<PaymentDTO> paymentDTOS = entityDtoConverter.convertPaymentsListToDTO(payments);


            System.out.println();

            model.addAttribute("accounts", accountDTOS);
            model.addAttribute("payments", paymentDTOS);

            int totalPagesAccount = accountDTOS.getTotalPages();
            if (totalPagesAccount > 0) {
                List<Integer> accountPageNumbers = IntStream.rangeClosed(1, totalPagesAccount)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("accountPageNumbers", accountPageNumbers);
            }

            int totalPagesPayments = paymentDTOS.getTotalPages();
            if (totalPagesPayments > 0) {
                List<Integer> paymentsPageNumbers = IntStream.rangeClosed(1, totalPagesPayments)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("paymentsPageNumbers", paymentsPageNumbers);
            }

        } catch (UnexpectedRollbackException e) {
            log.info("error in transaction when open profile");
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("no user when open profile");
            model.addAttribute("noUserError", true);
        }
        return "user/profile";
    }

    @PostMapping("/send")
    public String send(@NotNull Long paymentId, Model model, RedirectAttributes redirectAttributes,
                       @RequestParam(required = false, defaultValue = "1") Integer accPage,
                       @RequestParam(required = false, defaultValue = "id") String accSortBy,
                       @RequestParam(required = false, defaultValue = "true") Boolean accAsc,
                       @RequestParam(required = false, defaultValue = "1") Integer payPage,
                       @RequestParam(required = false, defaultValue = "id") String paySortBy,
                       @RequestParam(required = false, defaultValue = "true") Boolean payAsc) {
        redirectAttributes.addAttribute("accPage", accPage);
        redirectAttributes.addAttribute("accSortBy", accSortBy);
        redirectAttributes.addAttribute("accAsc", accAsc);
        redirectAttributes.addAttribute("payPage", payPage);
        redirectAttributes.addAttribute("paySortBy", paySortBy);
        redirectAttributes.addAttribute("payAsc", payAsc);
        try {
            Payment payment = paymentService.findById(paymentId).orElseThrow(() -> new NotFoundException("No such payment"));
            if (payment.getAccount().isBan()) {
                throw new BanException("account was banned");
            }
            paymentService.sendPayment(payment);
            log.info("send payment with id {}", paymentId);
            return "redirect:/user/profile";
        } catch (NotFoundException e) {
            log.info("no payment when send payment with id {}", paymentId);
            redirectAttributes.addAttribute("noPaymentError", true);
        } catch (NotEnoughMoneyException e) {
            log.info("not enough money when send payment with id {}", paymentId);
            redirectAttributes.addAttribute("noMoneyError", true);
        } catch (UnexpectedRollbackException e) {
            log.info("something went wrong with transaction when send payment with id {}", paymentId);
            redirectAttributes.addAttribute("error", true);
        } catch (BanException e) {
            log.info("account was banned when send payment with id {}", paymentId);
            redirectAttributes.addAttribute("banError", true);
        }
        return "redirect:/user/profile/send";
    }

    @GetMapping("/send")
    public String sendGet(@NotNull Long paymentId, Model model,
                          @RequestParam(required = false, defaultValue = "false") Boolean noPaymentError,
                          @RequestParam(required = false, defaultValue = "false") Boolean noMoneyError,
                          @RequestParam(required = false, defaultValue = "false") Boolean error,
                          @RequestParam(required = false, defaultValue = "false") Boolean banError,
                          @RequestParam(required = false, defaultValue = "1") Integer accPage,
                          @RequestParam(required = false, defaultValue = "id") String accSortBy,
                          @RequestParam(required = false, defaultValue = "true") Boolean accAsc,
                          @RequestParam(required = false, defaultValue = "1") Integer payPage,
                          @RequestParam(required = false, defaultValue = "id") String paySortBy,
                          @RequestParam(required = false, defaultValue = "true") Boolean payAsc) {

        model.addAttribute("accPage", accPage);
        model.addAttribute("accSortBy", accSortBy);
        model.addAttribute("accAsc", accAsc);
        model.addAttribute("payPage", payPage);
        model.addAttribute("paySortBy", paySortBy);
        model.addAttribute("payAsc", payAsc);

        model.addAttribute("noPaymentError", noPaymentError);
        model.addAttribute("noMoneyError", noMoneyError);
        model.addAttribute("error", error);
        model.addAttribute("banError", banError);

        return "user/paymentSendResult";
    }

}
