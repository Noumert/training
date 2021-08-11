package project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.EntityDtoConverter;
import project.dto.UserDTO;
import project.entity.RoleType;
import project.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
@Validated
@RequestMapping("/admin/users")
public class UsersAdministrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @RequestMapping()
    public String usersPage(Model model) {

        List<UserDTO> userDTOS = entityDtoConverter.convertUserListToUserDto(userService.findByRole(RoleType.ROLE_USER));
        model.addAttribute("users", userDTOS);
        return "admin/usersAdministration";
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long userId, Model model){
        try {
            log.info("lock user accountNonLocked {} userId {}",false,userId);
            userService.setAccountNonLockedById(false,userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/admin/userBlockResult";
        }
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long userId, Model model){
        try {
            log.info("unlock user accountNonLocked {} userId {}",true,userId);
            userService.setAccountNonLockedById(true,userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            model.addAttribute("error",true);
            return "/admin/userBlockResult";
        }
    }

}
