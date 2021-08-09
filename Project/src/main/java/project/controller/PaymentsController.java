package project.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.model.entity.Payment;
import project.model.entity.StatusType;
import project.model.service.AccountService;
import project.model.service.PaymentService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/user/payments")
public class PaymentsController {
    @Autowired
    private EntityDtoConverter entityDtoConverter;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaymentService paymentService;


    @RequestMapping()
    public String paymentsPage(Model model) {
        try {
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(accountService.findCurrentUserAccounts()));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/payments";
    }

    @PostMapping(value="/prepare")
    public String prepare(@NotNull Long accountId,@NotNull @Min(value = 1L, message = "min top up is 1")
    @Max(value = 99999L, message = "max top up is 99999") Long money, @NotEmpty @NotNull String recipient, Model model) {
        try {
            Payment payment = Payment
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .money(money)
                    .account(accountService.findById(accountId))
                    .recipient(recipient)
                    .status(StatusType.PREPARED)
                    .build();
            paymentService.saveNewPayment(payment);
            model.addAttribute("success", true);
            return "/user/paymentPrepareResult";
        } catch (NotFoundException | RuntimeException e) {
            model.addAttribute("error", true);
            return "/user/paymentPrepareResult";
        }
    }


}