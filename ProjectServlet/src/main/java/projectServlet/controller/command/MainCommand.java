package projectServlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class MainCommand implements Command{
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Load main page");
        return "index.jsp";
    }
}
