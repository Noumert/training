package projectServlet.controller.command.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.entity.Account;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class AdminAccountUnbanCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    AccountService accountService = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Account account = accountService.findById(accountId).orElseThrow(NotFoundException::new);
        boolean ban = true;
        accountService.setBanByAccount(ban, account);
        logger.info("account successfully unbanned");
        return "redirect:/admin/accounts";
    }
}
