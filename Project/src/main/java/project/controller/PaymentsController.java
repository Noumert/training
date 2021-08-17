package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dto.FillPaymentDTO;
import project.entity.Account;
import project.entity.MyUserDetails;
import project.entity.Payment;
import project.entity.StatusType;
import project.model.EntityDtoConverter;
import project.model.MoneyParser;
import project.service.AccountService;
import project.service.PaymentService;
import project.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

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
    @Autowired
    private ControllerUtils controllerUtils;


    @RequestMapping()
    public String paymentsPage(Model model) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
                accountService.findUserAccountsByUserId(currentUserId)));
        model.addAttribute("payment", new FillPaymentDTO());
        return "user/payments";
    }

    @PostMapping(value = "/prepare")
    public String prepare(@ModelAttribute("payment") @Valid FillPaymentDTO fillPaymentDTO,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            Map<String, String> errorsMap = controllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
                    accountService.findUserAccountsByUserId(currentUserId)));
            return "user/payments";
        }
        long moneyValue = moneyParser.getMoneyValue(fillPaymentDTO.getPaymentMoney());
        try {
            Account account = accountService.findById(fillPaymentDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
            Payment payment = Payment
                    .builder()
                    .dateTime(LocalDateTime.now())
                    .money(moneyValue)
                    .account(account)
                    .recipient(fillPaymentDTO.getRecipient())
                    .status(StatusType.PREPARED)
                    .build();
            paymentService.saveNewPayment(payment);
            log.info("prepare payment accountId : {} moneyUAHValue : {}", fillPaymentDTO.getAccountId(), moneyValue);
            model.addAttribute("success", true);
            return "/user/paymentPrepareResult";
        } catch (NotFoundException e) {
            log.info("no account with Id {}",fillPaymentDTO.getAccountId());
            model.addAttribute("noAccountError", true);
        } catch (RuntimeException e) {
            log.info("some generated parameter was not unique when trying to prepare payment with id {}", fillPaymentDTO.getAccountId());
            model.addAttribute("duplicatedError", true);
        }
        return "/user/paymentPrepareResult";
    }

    //TODO prg

}
