package projectServlet.controller.command.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class UserAccountTopUpForm implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter= new AccountDtoConverterImpl();
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        request.setAttribute("account",
                accountDtoConverter
                        .convertEntityToDto(accountService.findById(accountId).orElseThrow(NotFoundException::new)));
        logger.info("load top up form");
        return "/WEB-INF/user/accountTopUpForm.jsp";
    }
}
