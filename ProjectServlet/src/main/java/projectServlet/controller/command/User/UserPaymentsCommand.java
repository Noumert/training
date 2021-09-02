package projectServlet.controller.command.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.CreditCardDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.CreditCardDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.CreditCard;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.CreditCardService;
import projectServlet.model.service.CreditCardServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class UserPaymentsCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final AccountService accountService = new AccountServiceImpl();
    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter= new AccountDtoConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("accounts",
                accountDtoConverter
                        .convertEntityListToDtoList(accountService.findByUserId(user.getId())));
        logger.info("payments page loaded");
        return "/WEB-INF/user/payments.jsp";
    }
}
