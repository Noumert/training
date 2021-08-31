package projectServlet.controller.command;

import projectServlet.config.PasswordEncoder;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if (name == null || name.equals("") || pass == null || pass.equals("")) {
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, name)) {
            request.setAttribute("userLoggedError", true);
            return "/WEB-INF/error.jsp";
        }

        Optional<User> userOpt = userService.authenticateUser(name, pass);

        if (!userOpt.isPresent()) {
            return "redirect:/login?error=true";
        } else {
            User user = userOpt.get();
            CommandUtility.setUser(request, user);
            CommandUtility.setUserRole(request, user.getRole(), user.getEmail());
        }
        
        return "redirect:/main";
    }

}
