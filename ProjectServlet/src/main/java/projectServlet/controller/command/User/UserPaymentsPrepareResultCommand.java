package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class UserPaymentsPrepareResultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/user/paymentPrepareResult.jsp";
    }
}
