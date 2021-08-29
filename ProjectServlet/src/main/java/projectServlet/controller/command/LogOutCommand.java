package projectServlet.controller.command;

import projectServlet.model.entity.RoleType;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.clearSessionAndContext(request);
        return "redirect:/main";
    }
}
