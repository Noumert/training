package projectServlet.controller.command;

import projectServlet.config.PasswordEncoder;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command{
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            return "/login.jsp";
        }

        Optional<User> userOpt = userService.authenticateUser(name, PasswordEncoder.encode(pass));
        User user;

        if(!userOpt.isPresent()){
            return "redirect:/login?error=true";
        }else {
            user = userOpt.get();
            CommandUtility.setUser(request,user);
        }

        if(CommandUtility.checkUserIsLogged(request, name)){
            return "/WEB-INF/error.jsp";
        }

        if (user.getRole().equals(RoleType.ROLE_ADMIN)){
            CommandUtility.setUserRole(request, RoleType.ROLE_ADMIN, name);
            return "redirect:/admin";
        } else if(user.getRole().equals(RoleType.ROLE_USER)) {
            CommandUtility.setUserRole(request, RoleType.ROLE_USER, name);
            return "redirect:/user";
        } else {
            return "redirect:/login";
        }
    }

}
