package projectServlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.model.entity.RoleType;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.clearSessionAndContext(request);
        logger.info("log out");
        return "redirect:/main";
    }
}
