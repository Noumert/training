package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.AccountUserAccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.UserAccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class UserAccountsCommand implements Command {
    private final AccountService accountService = new AccountServiceImpl();
    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter= new AccountDtoConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("accounts",
                accountDtoConverter
                        .convertEntityListToDtoList(accountService.findByUserId(user.getId())));
        return "/WEB-INF/user/accounts.jsp";
    }
}
