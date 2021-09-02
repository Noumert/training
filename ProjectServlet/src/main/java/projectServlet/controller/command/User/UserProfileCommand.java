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
    private final int PAGE_SIZE=5;
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final AccountService accountService = new AccountServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final EntityDtoConverter<Account, AccountDTO> accountDtoConverter= new AccountDtoConverterImpl();
    private final EntityDtoConverter<Payment, PaymentDTO> paymentDtoConverter= new PaymentDtoConverterImpl();
    private final EntityDtoConverter<User, UserDTO> userDtoConverter= new UserDtoConverterImpl();
    @Override
    public String execute(HttpServletRequest request) {
        int accPage = Integer.parseInt(Optional.ofNullable(request.getParameter("accPage")).orElse("1"));
        String accSortBy = Optional.ofNullable(request.getParameter("accSortBy")).orElse("account_id");
        boolean accAsc = Boolean.parseBoolean(Optional.ofNullable(request.getParameter("accAsc")).orElse("true"));
        int payPage = Integer.parseInt(Optional.ofNullable(request.getParameter("payPage")).orElse("1"));
        String paySortBy = Optional.ofNullable(request.getParameter("paySortBy")).orElse("payment_id");
        boolean payAsc = Boolean.parseBoolean(Optional.ofNullable(request.getParameter("payAsc")).orElse("true"));
        request.setAttribute("accPage", accPage);
        request.setAttribute("accSortBy", accSortBy);
        request.setAttribute("accAsc", accAsc);
        request.setAttribute("payPage", payPage);
        request.setAttribute("paySortBy", paySortBy);
        request.setAttribute("payAsc", payAsc);


        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("user",userDtoConverter.convertEntityToDto(user));
        request.setAttribute("accounts",
                accountDtoConverter
                        .convertEntityListToDtoList(
                                accountService.findByUserId(user.getId(),accPage,PAGE_SIZE,accSortBy,accAsc)
                        ));
        request.setAttribute("payments",
                paymentDtoConverter
                        .convertEntityListToDtoList(
                                paymentService.findByUserId(user.getId(),payPage,PAGE_SIZE,paySortBy,payAsc)
                        ));

        request.setAttribute("accTotal", (accountService.findByUserId(user.getId()).size()/PAGE_SIZE)+1);

        request.setAttribute("payTotal",  (paymentService.findByUserId(user.getId()).size()/PAGE_SIZE)+1);

        logger.info("Load Profile page");
        return "/WEB-INF/user/profile.jsp";
    }
}
