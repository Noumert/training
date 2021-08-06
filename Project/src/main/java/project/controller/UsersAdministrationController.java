package project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.model.dto.UserAccountDTO;
import project.model.dto.UserDTO;
import project.model.entity.RoleType;
import project.model.service.AccountService;
import project.model.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/users")
public class UsersAdministrationController {
    @Autowired
    UserService userService;

    @Autowired
    EntityDtoConverter entityDtoConverter;

    @RequestMapping()
    public String usersPage(Model model) {

        List<UserDTO> userDTOS = entityDtoConverter.convertUserListToUserDto(userService.findByRole(RoleType.ROLE_USER));
        model.addAttribute("users", userDTOS);
        return "admin/usersAdministration";
    }

    @PostMapping("/ban")
    public String banAccount(@Valid @NotNull @NotEmpty Long userId, Model model){
        try {
            userService.setBanById(false,userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/admin/userBlockResult";
        }
    }

    @PostMapping("/unban")
    public String unbanAccount(@Valid @NotNull @NotEmpty Long userId, Model model){
        try {
            userService.setBanById(true,userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/admin/userBlockResult";
        }
    }

}
