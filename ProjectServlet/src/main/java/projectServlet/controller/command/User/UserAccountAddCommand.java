package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class UserAccountAddCommand implements Command {
    private final long START_MONEY_VALUE = 0L;
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        accountService.save(Account.builder()
                .accountName("U" + UUID.randomUUID())
                .accountNumber(UUID.randomUUID().toString())
                .ban(false)
                .money(START_MONEY_VALUE)
                .user(user)
                .build()
        );
        return "redirect:/user/accounts";
    }
}
