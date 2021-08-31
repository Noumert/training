package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.converters.MoneyFormatConverter;
import projectServlet.model.converters.MoneyFormatConverterImpl;
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
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final MoneyFormatConverter moneyFormatConverter = new MoneyFormatConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        //TODO add validation
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long moneyValue = moneyFormatConverter.getMoneyValue(request.getParameter("paymentMoney"));
        String recipient = request.getParameter("recipient");
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
        }catch (RuntimeException e){
//            System.err.println(e.getMessage());
            return "redirect:/user/payments/prepareResult?error=true";
        }
    }
}
