package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.model.EntityDtoConverter;
import project.dto.UserDTO;
import project.entity.RoleType;
import project.entity.User;
import project.exceptions.DuplicatedEmailException;
import project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private ControllerUtils controllerUtils;
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
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto,
                                      BindingResult bindingResult,
                                      HttpServletRequest request,
                                      Model model,
                                      Errors errors) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = controllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
        } else {
            try {
                User user = entityDtoConverter.convertUserDTOToUser(userDto);
                user.setRole(RoleType.ROLE_USER);
                user.setAccountNonLocked(true);
                userService.saveNewUser(user);
                model.addAttribute("success", true);
            } catch (DuplicatedEmailException e) {
                userDto.setPassword("");
                model.addAttribute("user", userDto);
                model.addAttribute("generalError", true);
                model.addAttribute("success", false);
            }
        }

        return "registration";
    }




}
