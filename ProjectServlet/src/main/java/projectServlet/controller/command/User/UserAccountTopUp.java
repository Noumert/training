package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.MoneyFormatConverter;
import projectServlet.model.converters.MoneyFormatConverterImpl;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class UserAccountTopUp implements Command {
    private final MoneyFormatConverter moneyFormatConverter = new MoneyFormatConverterImpl();
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        //TODO validation
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long moneyValue =  moneyFormatConverter.getMoneyValue(request.getParameter("topUpMoney"));

        accountService.addMoneyById(moneyValue,accountId);

        return "redirect:/user/accounts";
    }
}
