package projectServlet.controller.command.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.UnbanAccountRequestService;
import projectServlet.model.service.UnbanAccountRequestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;

public class UserAccountUnbanCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final UnbanAccountRequestService unbanAccountRequestService =
            new UnbanAccountRequestServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();


    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Account account = accountService.findById(accountId).orElseThrow(NotFoundException::new);
        unbanAccountRequestService.save(UnbanAccountRequest.builder()
                .dateTime(LocalDateTime.now())
                .resolved(false)
                .account(account)
                .build()
        );
        logger.info("account unban request sent successfully");
        return "redirect:/user/accounts/unbanResult?success=true";
    }
}
