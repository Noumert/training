package projectServlet.controller.command.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.PaymentDtoConverterImpl;
import projectServlet.model.converters.UserDtoConverterImpl;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.PaymentDTO;
import projectServlet.model.dto.UserDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.User;
import projectServlet.model.service.AccountService;
import projectServlet.model.service.AccountServiceImpl;
import projectServlet.model.service.PaymentService;
import projectServlet.model.service.PaymentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserProfileCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final AccountService accountService = new AccountServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter= new AccountDtoConverterImpl();
    private final EntityDtoConverter<Payment, PaymentDTO> paymentDtoConverter= new PaymentDtoConverterImpl();
    private final EntityDtoConverter<User, UserDTO> userDtoConverter= new UserDtoConverterImpl();
    @Override
    public String execute(HttpServletRequest request) {
        Optional<String> accPage = Optional.ofNullable(request.getParameter("accPage"));
        Optional<String> accSortBy = Optional.ofNullable(request.getParameter("accSortBy"));
        Optional<String> accAsc = Optional.ofNullable(request.getParameter("accAsc"));
        Optional<String> payPage = Optional.ofNullable(request.getParameter("payPage"));
        Optional<String> paySortBy = Optional.ofNullable(request.getParameter("paySortBy"));
        Optional<String> payAsc = Optional.ofNullable(request.getParameter("payAsc"));
        request.setAttribute("accPage", accPage.orElse("1"));
        request.setAttribute("accSortBy", accSortBy.orElse("account_id"));
        request.setAttribute("accAsc", accAsc.orElse("true"));
        request.setAttribute("payPage", payPage.orElse("1"));
        request.setAttribute("paySortBy", paySortBy.orElse("payment_id"));
        request.setAttribute("payAsc", payAsc.orElse("true"));

        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("user",userDtoConverter.convertEntityToDto(user));
        request.setAttribute("accounts",
                accountDtoConverter
                        .convertEntityListToDtoList(accountService.findByUserId(user.getId())));
        request.setAttribute("payments",
                paymentDtoConverter
                        .convertEntityListToDtoList(paymentService.findByUserId(user.getId())));
        logger.info("Load Profile page");
        return "/WEB-INF/user/profile.jsp";
    }
}
