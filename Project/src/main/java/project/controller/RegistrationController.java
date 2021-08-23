package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dto.UserDTO;
import project.entity.RoleType;
import project.entity.User;
import project.model.EntityDtoConverter;
import project.service.UserService;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Noumert on 11.08.2021.
 */
@Controller
public class RegistrationController {
    @Autowired
    private ControllerUtils controllerUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private EntityDtoConverter<User, UserDTO> userDtoConverter;

    @GetMapping(value = {"/registration"})
    public String showRegistrationForm(Model model, @RequestParam(required = false, defaultValue = "false") Boolean success,
                                       @RequestParam(required = false, defaultValue = "false") Boolean generalError) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        model.addAttribute("success", success);
        model.addAttribute("generalError", generalError);
        return "registration";
    }

    @PostMapping(value = {"/registration"})
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = controllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        } else {
            try {
                User user = userDtoConverter.convertDtoToEntity(userDto);
                user.setRole(RoleType.ROLE_USER);
                user.setAccountNonLocked(true);
                userService.save(user);
                redirectAttributes.addAttribute("success", true);
            } catch (RuntimeException e) {
                redirectAttributes.addAttribute("generalError", true);
                redirectAttributes.addAttribute("success", false);
            }
        }

        return "redirect:/registration";
    }


}
