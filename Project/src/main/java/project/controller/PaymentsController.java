package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dto.FillPaymentDTO;
import project.entity.Account;
import project.entity.MyUserDetails;
import project.entity.Payment;
import project.entity.StatusType;
import project.model.EntityDtoConverter;
import project.model.MoneyFormatConverter;
import project.service.AccountServiceImpl;
import project.service.PaymentServiceImpl;
import project.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user/payments")
public class PaymentsController {
    @Autowired
    private EntityDtoConverter entityDtoConverter;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private MoneyFormatConverter moneyFormatConverter;
    @Autowired
    private ControllerUtils controllerUtils;


    @RequestMapping()
    public String paymentsPage(Model model) {
        Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
                accountService.findByUserId(currentUserId)));
        model.addAttribute("payment", new FillPaymentDTO());
        return "user/payments";
    }

    @PostMapping(value = "/prepare")
    public String prepare(@ModelAttribute("payment") @Valid FillPaymentDTO fillPaymentDTO,
                          BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Long currentUserId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            Map<String, String> errorsMap = controllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("accounts", entityDtoConverter.convertAccountsListToDTO(
                    accountService.findByUserId(currentUserId)));
            return "user/payments";
        }
        long moneyValue = moneyFormatConverter.getMoneyValue(fillPaymentDTO.getPaymentMoney());
        try {
            Account account = accountService.findById(fillPaymentDTO.getAccountId()).orElseThrow(() -> new NotFoundException("no such account"));
            Payment payment = Payment
                    .builder()
                    .paymentNumber(UUID.randomUUID().toString())
                    .dateTime(LocalDateTime.now())
                    .money(moneyValue)
                    .account(account)
                    .recipient(fillPaymentDTO.getRecipient())
                    .status(StatusType.PREPARED)
                    .build();
            paymentService.save(payment);
            log.info("prepare payment accountId : {} moneyUAHValue : {}", fillPaymentDTO.getAccountId(), moneyValue);
            redirectAttributes.addAttribute("success", true);
        } catch (NotFoundException e) {
            log.info("no account with Id {}",fillPaymentDTO.getAccountId());
            redirectAttributes.addAttribute("noAccountError", true);
        } catch (RuntimeException e) {
            log.info("some generated parameter was not unique when trying to prepare payment with id {}", fillPaymentDTO.getAccountId());
            redirectAttributes.addAttribute("duplicatedError", true);
        }
        return "redirect:/user/payments/prepare";
    }

    @GetMapping(value = "/prepare")
    public String prepareGet(Model model,
                             @RequestParam(required = false, defaultValue = "false") Boolean success,
                             @RequestParam(required = false, defaultValue = "false") Boolean noAccountError,
                             @RequestParam(required = false, defaultValue = "false") Boolean duplicatedError) {

            model.addAttribute("success", success);
            model.addAttribute("noAccountError", noAccountError);
            model.addAttribute("duplicatedError", duplicatedError);

        return "/user/paymentPrepareResult";
    }
}
