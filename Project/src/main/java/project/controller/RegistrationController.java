package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import project.dto.UserDTO;
import project.entity.User;
import project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"/registration"})
    public String showRegistrationForm(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping(value = {"/registration"})
    public String registerUserAccount(UserDTO userDto) {
        //TODO check userDto correct
        userService.saveNewUser(userDto);
        return "regDone";
    }
}
