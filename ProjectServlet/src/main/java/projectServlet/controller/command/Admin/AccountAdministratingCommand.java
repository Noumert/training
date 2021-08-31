package projectServlet.controller.command.Admin;

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
    AccountService accountService = new AccountServiceImpl();
    EntityDtoConverter<Account, UserAccountDTO> accountUserAccountDtoConverter= new AccountUserAccountDtoConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("userAccounts",
                accountUserAccountDtoConverter
                .convertEntityListToDtoList(accountService.findAll()));
        return "/WEB-INF/admin/accountsAdministration.jsp";
    }
}
