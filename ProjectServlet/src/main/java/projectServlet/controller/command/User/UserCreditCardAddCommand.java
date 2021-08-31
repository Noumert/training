package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.CreditCard;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.CreditCardService;
import projectServlet.model.service.CreditCardServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.UUID;

public class UserCreditCardAddCommand implements Command {
    private final AccountService accountService = new AccountServiceImpl();
    private final CreditCardService creditCardService = new CreditCardServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        User user = (User) request.getSession().getAttribute("user");
        Account account = accountService.findById(accountId).orElseThrow(NotFoundException::new);
        creditCardService.save(CreditCard.builder()
                .cardNumber(UUID.randomUUID().toString())
                .expirationDate(LocalDate.now())
                .account(account)
                .user(user)
                .build()
        );
        return "redirect:/user/creditCards";
    }
}
