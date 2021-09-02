package projectServlet.controller.command.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class AdminUserUnbanCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("userId"));
        boolean accountNonLocked = true;
        userService.setAccountNonLockedByUser(accountNonLocked,
                userService.findById(userId).orElseThrow(NotFoundException::new));
        logger.info("user successfully unbanned");
        return "redirect:/admin/users";
    }
}
