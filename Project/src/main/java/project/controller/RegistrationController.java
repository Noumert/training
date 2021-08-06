package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.model.EntityDtoConverter;
import project.model.dto.UserDTO;
import project.model.entity.RoleType;
import project.model.entity.User;
import project.exceptions.DuplicatedEmailException;
import project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @Autowired
    EntityDtoConverter entityDtoConverter;

    @GetMapping(value = {"/registration"})
    public String showRegistrationForm(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping(value = {"/registration"})
    public String registerUserAccount(@Valid UserDTO userDto,
                                      HttpServletRequest request,
                                      Model model,
                                      Errors errors) {
        try {
            User user = entityDtoConverter.convertUserDtoToUser(userDto);
            user.setRole(RoleType.ROLE_USER);
            user.setAccountNonLocked(true);
            userService.saveNewUser(user);
            model.addAttribute("success", true);
        } catch (DuplicatedEmailException e) {
            model.addAttribute("error", true);
            model.addAttribute("success", false);
        }
        model.addAttribute("user", userDto);
        return "registration";
    }


}
