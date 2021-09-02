package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.model.GlobalConstants;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.MoneyFormatConverter;
import projectServlet.model.converters.MoneyFormatConverterImpl;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class UserAccountTopUp implements Command {
    private final MoneyFormatConverter moneyFormatConverter = new MoneyFormatConverterImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter= new AccountDtoConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String accountIdString = request.getParameter("accountId");

        String moneyString = request.getParameter("topUpMoney");

        if (accountIdString == null || accountIdString.equals("")
                || moneyString == null || moneyString.equals("")) {
            return "redirect:/user/accounts/topUpForm";
        }

        Long accountId = Long.valueOf(accountIdString);
        long moneyValue = moneyFormatConverter.getMoneyValue(moneyString);

        boolean haveErrors=false;

        if (moneyValue < GlobalConstants.MIN_MONEY_VALUE || moneyValue > GlobalConstants.MAX_MONEY_VALUE) {
            request.setAttribute("moneyIncorrect", true);
            haveErrors = true;
        }

        if (haveErrors) {
            request.setAttribute("paymentMoney", moneyString);
            request.setAttribute("account",
                    accountDtoConverter
                            .convertEntityToDto(accountService.findById(accountId).orElseThrow(NotFoundException::new)));
            return "/WEB-INF/user/accountTopUpForm.jsp";
        }

        accountService.addMoneyById(moneyValue,accountId);

        return "redirect:/user/accounts";
    }
}
