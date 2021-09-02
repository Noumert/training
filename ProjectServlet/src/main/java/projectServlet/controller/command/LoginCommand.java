package projectServlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.config.PasswordEncoder;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if (name == null || name.equals("") || pass == null || pass.equals("")) {
            logger.info("Load login page");
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, name)) {
            request.setAttribute("userLoggedError", true);
            logger.info("Already logged user error");
            return "/WEB-INF/error.jsp";
        }

        Optional<User> userOpt = userService.authenticateUser(name, pass);

        if (!userOpt.isPresent()) {
            logger.info("incorrect user or password");
            return "redirect:/login?error=true";
        } else {
            User user = userOpt.get();
            CommandUtility.setUser(request, user);
            CommandUtility.setUserRole(request, user.getRole(), user.getEmail());
            logger.info("successful log in");
        }
        
        return "redirect:/main";
    }

}
