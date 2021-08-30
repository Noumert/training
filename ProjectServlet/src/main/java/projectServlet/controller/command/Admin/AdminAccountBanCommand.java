package projectServlet.controller.command.Admin;

import projectServlet.controller.command.Command;
import projectServlet.model.entity.Account;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;


public class AdminAccountBanCommand implements Command {
    AccountService accountService = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Account account = accountService.findById(accountId).orElseThrow(NotFoundException::new);
        boolean ban = true;
        accountService.setBanByAccount(ban, account);
        return "redirect:/admin/accounts";
    }
}
