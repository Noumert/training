package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.entity.Account;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class UserAccountBanCommand implements Command {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        boolean ban = true;
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Account account = accountService.findById(accountId).orElseThrow(NotFoundException::new);
        accountService.setBanByAccount(ban, account);
        return "redirect:/user/accounts";
    }
}
