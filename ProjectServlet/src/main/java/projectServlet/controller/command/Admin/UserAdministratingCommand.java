package projectServlet.controller.command.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.UserDtoConverterImpl;
import projectServlet.model.dto.UserDTO;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;
import projectServlet.model.service.UserService;
import projectServlet.model.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class UserAdministratingCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    UserService userService = new UserServiceImpl();
    EntityDtoConverter<User, UserDTO> userDtoConverter = new UserDtoConverterImpl();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("users",
                userDtoConverter
                        .convertEntityListToDtoList(userService.findByRole(RoleType.ROLE_USER)));
        logger.info("load users administrating page");
        return "/WEB-INF/admin/usersAdministration.jsp";
    }
}
