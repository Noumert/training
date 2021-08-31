package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserSendPaymentResultCommand implements Command {

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

        return "/WEB-INF/user/paymentSendResult.jsp";
    }
}
