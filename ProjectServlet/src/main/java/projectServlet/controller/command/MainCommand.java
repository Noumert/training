package projectServlet.controller.command;

import javax.servlet.http.HttpServletRequest;

public class MainCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return "index.jsp";
    }
}
