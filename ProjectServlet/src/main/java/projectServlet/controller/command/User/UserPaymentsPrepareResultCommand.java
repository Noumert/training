package projectServlet.controller.command.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class UserPaymentsPrepareResultCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public String execute(HttpServletRequest request) {

        logger.info("payment preparing result loaded");

        return "/WEB-INF/user/paymentPrepareResult.jsp";
    }
}
