package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.model.EntityDtoConverter;
import project.dto.UserDTO;
import project.entity.RoleType;
import project.entity.User;
import project.exceptions.DuplicatedEmailException;
import project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Validated
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;

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
            User user = entityDtoConverter.convertUserDTOToUser(userDto);
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
