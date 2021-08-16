package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.entity.User;
import project.model.EntityDtoConverter;
import project.dto.UserDTO;
import project.entity.RoleType;
import project.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
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
    public String banAccount(@NotNull Long userId, Model model) {
        try {
            User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("no such user"));
            userService.setAccountNonLockedByUser(false, user);
            log.info("lock user accountNonLocked {} userId {}", false, userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            log.info("something went wrong when lock user accountNonLocked {} userId {}", false, userId);
            model.addAttribute("error", true);
        } catch (NotFoundException e) {

        }
        return "/admin/userBlockResult";
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long userId, Model model) {
        try {
            User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("no such user"));
            userService.setAccountNonLockedByUser(true, user);
            log.info("unlock user accountNonLocked {} userId {}", true, userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            log.info("something went wrong when unlock user accountNonLocked {} userId {}", true, userId);
            model.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("no user with id {}",userId);
            model.addAttribute("noUserError", true);
        }
        return "/admin/userBlockResult";
    }

}
