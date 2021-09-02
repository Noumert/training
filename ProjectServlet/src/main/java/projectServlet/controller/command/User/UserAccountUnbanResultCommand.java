package projectServlet.controller.command.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class UserAccountUnbanResultCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("user account unban request results loaded");
        return "/WEB-INF/user/accountUnbanResult.jsp";
    }
}
