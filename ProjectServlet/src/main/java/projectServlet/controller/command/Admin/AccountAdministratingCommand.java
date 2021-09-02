package projectServlet.controller.command.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.converters.AccountUserAccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.dto.UserAccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.RoleType;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class AccountAdministratingCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    AccountService accountService = new AccountServiceImpl();
    EntityDtoConverter<Account, UserAccountDTO> accountUserAccountDtoConverter= new AccountUserAccountDtoConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("userAccounts",
                accountUserAccountDtoConverter
                .convertEntityListToDtoList(accountService.findAll()));
        logger.info("Account administrating page");
        return "/WEB-INF/admin/accountsAdministration.jsp";
    }
}
