package projectServlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.config.PasswordEncoder;
import projectServlet.controller.filter.AuthFilter;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public String execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (firstName == null || firstName.equals("") ||
                lastName == null || lastName.equals("") ||
                email == null || email.equals("") ||
                password == null || password.equals("")
        ) {
            logger.info("Load registration form");
            return "/registration.jsp";
        }

        boolean haveErrors = false;

        if(!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")){
            request.setAttribute("emailIncorrect",true);
            haveErrors = true;
        }

        if(!password.matches("^.{8,16}$")){
            request.setAttribute("passwordIncorrect",true);
            haveErrors = true;
        }

        if(haveErrors){
            request.setAttribute("firstName",firstName);
            request.setAttribute("lastName",lastName);
            request.setAttribute("email",email);
            logger.info("Load registration with validation errors");
            return "/registration.jsp";
        }

        try {
                     userService.save(User.builder()
                    .password(PasswordEncoder.passwordEncoder().encode(password))
                    .email(email)
                    .accountNonLocked(true)
                    .lastName(lastName)
                    .firstName(firstName)
                    .role(RoleType.ROLE_USER)
                    .build());
        }catch (RuntimeException e){
            logger.info("Problems with registration {}",e.getMessage());
            return "redirect:/registration?error=true";
        }
        return "redirect:/login";
    }
}
