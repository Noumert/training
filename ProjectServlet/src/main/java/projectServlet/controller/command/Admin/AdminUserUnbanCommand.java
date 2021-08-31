package projectServlet.controller.command.Admin;

import projectServlet.controller.command.Command;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class AdminUserUnbanCommand implements Command {
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("userId"));
        boolean accountNonLocked = true;
        userService.setAccountNonLockedByUser(accountNonLocked,
                userService.findById(userId).orElseThrow(NotFoundException::new));
        return "redirect:/admin/users";
    }
}
