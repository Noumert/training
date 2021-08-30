package projectServlet.controller.command.Admin;

import projectServlet.controller.command.Command;
import projectServlet.model.entity.RoleType;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class UserAdministratingCommand implements Command {
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        userService.findAll().forEach(System.out::println);
        request.setAttribute("users",userService.findByRole(RoleType.ROLE_USER));
        return "/WEB-INF/admin/usersAdministration.jsp";
    }
}
