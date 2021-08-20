package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.entity.User;
import project.model.EntityDtoConverterOlolo;
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

    @RequestMapping()
    public String usersPage(Model model) {
        List<UserDTO> userDTOS = entityDtoConverter.convertUserListToUserDTO(userService.findByRole(RoleType.ROLE_USER));
        model.addAttribute("users", userDTOS);
        return "admin/usersAdministration";
    }

    @PostMapping("/ban")
    public String banAccount(@NotNull Long userId, Model model,RedirectAttributes redirectAttributes) {
        boolean accountNonLocked = false;
        try {
            User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("no such user"));
            userService.setAccountNonLockedByUser(accountNonLocked, user);
            log.info("lock user accountNonLocked {} userId {}", accountNonLocked, userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            log.info("something went wrong when lock user accountNonLocked {} userId {}", accountNonLocked, userId);
            redirectAttributes.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("no user with id {}",userId);
            redirectAttributes.addAttribute("noUserError", true);
        }
        return "redirect:/admin/users/ban";
    }

    @GetMapping("/ban")
    public String banAccountGet(Model model,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noUserError,
                                  @RequestParam(required = false, defaultValue = "false") Boolean error) {

        model.addAttribute("noUserError", noUserError);
        model.addAttribute("error", error);

        return "/admin/userBlockResult";
    }

    @PostMapping("/unban")
    public String unbanAccount(@NotNull Long userId, Model model, RedirectAttributes redirectAttributes) {
        boolean accountNonLocked = true;
        try {
            User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("no such user"));
            userService.setAccountNonLockedByUser(accountNonLocked, user);
            log.info("unlock user accountNonLocked {} userId {}", accountNonLocked, userId);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            log.info("something went wrong when unlock user accountNonLocked {} userId {}", accountNonLocked, userId);
            redirectAttributes.addAttribute("error", true);
        } catch (NotFoundException e) {
            log.info("no user with id {}",userId);
            redirectAttributes.addAttribute("noUserError", true);
        }
        return "redirect:/admin/users/unban";
    }

    @GetMapping("/unban")
    public String unbanAccountGet(Model model,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noUserError,
                                  @RequestParam(required = false, defaultValue = "false") Boolean error) {

        model.addAttribute("noUserError", noUserError);
        model.addAttribute("error", error);

        return "/admin/userBlockResult";
    }

    //TODO prg

}
