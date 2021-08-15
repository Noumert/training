package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.entity.MyUserDetails;
import project.entity.Payment;
import project.entity.StatusType;
import project.model.MoneyParser;
import project.service.AccountService;
import project.service.PaymentService;
import project.service.UserService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequestMapping("/user/payments")
public class PaymentsController {
    @Autowired
    private EntityDtoConverter entityDtoConverter;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private MoneyParser moneyParser;


    @RequestMapping()
    public String paymentsPage(Model model) {
        Long currentUserId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            model.addAttribute("accounts",entityDtoConverter.convertAccountsListToDTO(
                    accountService.findUserAccountsByUserId(currentUserId)));
        } catch (NotFoundException | UnexpectedRollbackException e) {
            model.addAttribute("error", true);
        }
        return "user/payments";
    }

    @PostMapping(value="/prepare")
    public String prepare(@NotNull Long accountId,@NotNull @Min(value = 1L, message = "min top up is 1")
    @Max(value = 99999L, message = "max top up is 99999") double money, @NotEmpty @NotNull String recipient, Model model) {
        long moneyValue = moneyParser.getMoneyValue(money);
        log.info("prepare payment accountId : {} moneyUAHValue : {}",accountId,moneyValue);
        try {
            Payment payment = Payment
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .money(moneyValue)
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
