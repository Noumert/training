package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.GlobalConstants;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.MoneyFormatConverter;
import projectServlet.model.converters.MoneyFormatConverterImpl;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.PaymentService;
import projectServlet.model.service.PaymentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserPaymentsPrepareCommand implements Command {

    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter = new AccountDtoConverterImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final MoneyFormatConverter moneyFormatConverter = new MoneyFormatConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {

        String accountIdString = request.getParameter("accountId");

        String moneyString = request.getParameter("paymentMoney");

        String recipient = request.getParameter("recipient");

        if (accountIdString == null || accountIdString.equals("")
                || moneyString == null || moneyString.equals("")
                || recipient == null || recipient.equals("")) {
            return "redirect:/user/payments";
        }

        Long accountId = Long.valueOf(accountIdString);
        long moneyValue = moneyFormatConverter.getMoneyValue(moneyString);

        boolean haveErrors = false;

        if (moneyValue < GlobalConstants.MIN_MONEY_VALUE || moneyValue > GlobalConstants.MAX_MONEY_VALUE) {
            request.setAttribute("moneyIncorrect", true);
            haveErrors = true;
        }

        if (haveErrors) {
            request.setAttribute("paymentMoney", moneyString);
            request.setAttribute("recipient", recipient);
            User user = (User) request.getSession().getAttribute("user");
            request.setAttribute("accounts",
                    accountDtoConverter
                            .convertEntityListToDtoList(accountService.findByUserId(user.getId())));
            return "/WEB-INF/user/payments.jsp";
        }

        try {
            Account account = accountService.findById(accountId).orElseThrow(NotFoundException::new);
            paymentService.save(Payment.builder()
                    .money(moneyValue)
                    .status(StatusType.PREPARED)
                    .recipient(recipient)
                    .account(account)
                    .dateTime(LocalDateTime.now())
                    .paymentNumber(UUID.randomUUID().toString())
                    .build());
            return "redirect:/user/payments/prepareResult?success=true";
        } catch (RuntimeException e) {
            return "redirect:/user/payments/prepareResult?error=true";
        }
    }
}
